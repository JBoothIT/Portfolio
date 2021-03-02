#lang racket

;; Assignment 4: A church-compiler for Scheme, to Lambda-calculus

(provide church-compile
         ; provided conversions:
         church->nat
         church->bool
         church->listof)


;; Input language:
;
; e ::= (letrec ([x (lambda (x ...) e)]) e)    
;     | (let ([x e] ...) e)  
;     | (let* ([x e] ...) e)
;     | (lambda (x ...) e)
;     | (e e ...)    
;     | x  
;     | (and e ...) | (or e ...)
;     | (if e e e)
;     | (prim e) | (prim e e)
;     | datum
; datum ::= nat | (quote ()) | #t | #f 
; nat ::= 0 | 1 | 2 | ... 
; x is a symbol
; prim is a primitive operation in list prims
; The following are *extra credit*: -, =, sub1  
(define prims '(+ * - = add1 sub1 cons car cdr null? not zero?))

; This input language has semantics identical to Scheme / Racket, except:
;   + You will not be provided code that yields any kind of error in Racket
;   + You do not need to treat non-boolean values as #t at if, and, or forms
;   + primitive operations are either strictly unary (add1 sub1 null? zero? not car cdr), 
;                                           or binary (+ - * = cons)
;   + There will be no variadic functions or applications---but any fixed arity is allowed

;; Output language:

; e ::= (lambda (x) e)
;     | (e e)
;     | x
;
; also as interpreted by Racket


;; Using the following decoding functions:

; A church-encoded nat is a function taking an f, and x, returning (f^n x)
(define (church->nat c-nat)
  ((c-nat add1) 0))

; A church-encoded bool is a function taking a true-thunk and false-thunk,
;   returning (true-thunk) when true, and (false-thunk) when false
(define (church->bool c-bool)
  ((c-bool (lambda (_) #t)) (lambda (_) #f)))

; A church-encoded cons-cell is a function taking a when-cons callback, and a when-null callback (thunk),
;   returning when-cons applied on the car and cdr elements
; A church-encoded cons-cell is a function taking a when-cons callback, and a when-null callback (thunk),
;   returning the when-null thunk, applied on a dummy value (arbitrary value that will be thrown away)
(define ((church->listof T) c-lst)
  ; when it's a pair, convert the element with T, and the tail with (church->listof T)
  ((c-lst (lambda (a) (lambda (b) (cons (T a) ((church->listof T) b)))))
   ; when it's null, return Racket's null
   (lambda (_) '())))


;; Write your church-compiling code below:

; churchify recursively walks the AST and converts each expression in the input language (defined above)
;   to an equivalent (when converted back via each church->XYZ) expression in the output language (defined above)
(define (churchify e)
  (define (prim? p) (if (member p prims) p #f))
  (define (p-splice p) (string->symbol (string-append "c-" (symbol->string p))))
  (match e
    ;LetRec
    [`(letrec ([,funct (lambda (,m ...) ,body)]) ,res)(churchify `(let ([,funct (ycomb (lambda (,funct) (lambda ,m ,body)))]) ,res))]
    
    ;Let
    [`(let ([,x ,rhs]) ,body) (churchify `((lambda (,x) ,body) ,rhs))]
    [`(let ([,x ,rhs] ...) ,body) (churchify `((lambda ,x ,body) ,@rhs))]

    ;Let*
    [`(let* ([,x ,rhs] ...) ,body) (churchifyc]

    
    ;Curry Lambda
    [`(lambda () ,body) `(lambda (_) ,(churchify body))]
    [`(lambda (,x) ,body) `(lambda (,x) ,(churchify body))]
    [`(lambda (,x ,y ...) ,body) `(lambda (,x) ,(churchify `(lambda ,y ,body)))]
    
    ;If statement
    [`(if ,exp ,then ,else) (churchify `(,exp (lambda () ,then) (lambda () ,else)))]

    ;And
    [`(and ,ef ,ez)(churchify `(if ,ef (if ,ez #t #f) #f))]
    [`(and ,ef ,ez ...) (churchify `(if ,ef (and ,@ez #f) #f))]
    
    ;Or
    [`(or ,ef ,ez)(churchify `(if ,ef #t (if ,ez #t #f)))]
    [`(or ,ef ,ez ...)(churchify `(if ,ef #t (or ,@ez #t) #f))]

    ;Primitives
    [`(,(? prim? p) ,exp ...)(churchify `(,(p-splice p) ,@exp))]

    ;Null Values
    [''() (churchify '(lambda (wc wn) (wn)))]
    
    ;Booleans
    [#t (churchify '(lambda (t f) (t)))]
    [#f (churchify '(lambda (t f) (f)))]
    
     ;Variable
    [(? symbol? x) x]
    
    ;Curry Applications
    [`(,ef) `(,(churchify ef) (lambda (x) x))]
    [`(,ef  ,earg)`(,(churchify ef) ,(churchify earg))]
    [`(,ef ,earg0 ,eargs ...) (churchify `((,ef ,earg0) ,@eargs))]

    ;Literals
    [(? integer? n)
     (define (wrap n)
       (if (zero? n)
           'x
           `(f ,(wrap (- n 1)))))
       (churchify `(lambda (f x) ,(wrap n)))]
    
    ;undefined operations
    [_ e]
    ))

; Takes a whole program in the input language, and converts it into an equivalent program in lambda-calc
(define (church-compile program)
  ; Define primitive operations and needed helpers using a top-level let form?
  (define church+ `(lambda (m n) (lambda (f x) (n f (m f x)))))
  (define church* `(lambda (m n) (lambda (f x) ((m (n f)) x))))
  (define churchAdd `(lambda (m) (lambda (f x) (f ((m f) x)))))
  (define zero? `(lambda (m) ((m (lambda (x) #f)) #t)))
  (define null? `(lambda (m) (m (lambda (f x) #f) (lambda () #t)))) ;something wrong with null
  (define cons `(lambda (m n) (lambda (wc wn) (wc m n))))
  (define ycomb `((lambda (u) (u u))(lambda (y) (lambda (f) (f (lambda (x) (((y y) f) x)))))))
  (define not `(lambda (m n f) (m f n)))
  (churchify
   `(let (;[c-add1 ,churchAdd]
          [c-+ ,church+]
          ;[c-* ,church*]
          ;[c-zero? ,zero?]
          ;[c-null? ,null?]
          ;[c-cons ,cons]
          ;[ycomb, ycomb]
         ;[-, church-]
          ;[c-not, not]
          )
      ,program)))


;Test Cases for testing inside DrRacket.
;(church->nat(eval (church-compile '(+ 1 (+ 2 (+ 3 3))))))
;(church->nat(eval (church-compile '(let* ([a 2] [b 3])(let* ([b 5] [c b])(* a (* b c)))))))
;(church->nat(eval (church-compile '(if (null? '())(let ([lst (cons '() (cons 3 '()))])(if (null? (car lst))(* 2 (car (cdr lst)))7))5))))
;(church->nat(eval (church-compile '(null? 1))))

;(define unchurch church->bool)
;(unchurch(eval(church-compile '#f)))
;(unchurch(eval(church-compile '(if #t #t #f))))
;(church->nat(eval(church-compile '(if (not #f)3(let ([U (lambda (u) (u u))])(U U))))))


;(define unchurch (church->listof church->nat))
;(unchurch(eval(church-compile '(cons 1 (cons 2 (cons 3 '()))))))