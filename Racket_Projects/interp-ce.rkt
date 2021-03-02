#lang racket

;; Assignment 3: A CE (Control and Environment) interpreter for Scheme

(provide interp-ce)

; Your task is to write a CE interpreter for a substantial subset of Scheme/Racket. 
; A CE interpreter is meta-circular to a large degree (e.g., a conditional in the target
; language (scheme-ir?) can be implemented using a conditional in the host language (Racket),
; recursive evaluation of a sub-expression can be implemented as a recursive call to the
; interpreter, however, it's characterized by creating an explicit closure value for lambdas
; that saves its static environment (the environment when it's defined). For example, a CE
; interpreter for the lambda calculus may be defined:
(define (interp-ce-lambda exp [env (hash)])
  (match exp
         [`(lambda (,x) ,body)
          ; Return a closure that pairs the code and current (definition) environment
          `(closure (lambda (,x) ,body) ,env)]
         [`(,efun ,earg)
          ; Evaluate both sub-expressions
          (define vfun (interp-ce-lambda vfun env))  
          (define varg (interp-ce-lambda earg env))
          ; the applied function must be a closure
          (match-define `(closure (lambda (,x) ,body) ,env+) vfun)
          ; we extend the *closure's environment* and interp the body
          (interp-ce-lambda body (hash-set env+ x varg))]
         [(? symbol? x)
          ; Look up a variable in the current environment
          (hash-ref env x)]))

; Following is a predicate for the target language you must support. You must support any
; syntax allowed by scheme-ir that runs without error in Racket, returning a correct value..
(define (scheme-ir? exp)
  ; You should support a few built-in functions bound to the following variables at the top-level
  (define (prim? s) (member s '(+ - * = equal? list cons car cdr null?)))
  (match exp
         [`(lambda ,(? (listof symbol?)) ,(? scheme-ir?)) #t] ; fixed arguments lambda
         [`(lambda ,(? symbol?) ,(? scheme-ir?)) #t] ; variable argument lambda
         [`(if ,(? scheme-ir?) ,(? scheme-ir?) ,(? scheme-ir?)) #t]
         [`(let ([,(? symbol?) ,(? scheme-ir?)] ...) ,(? scheme-ir?)) #t]
         [`(let* ([,(? symbol?) ,(? scheme-ir?)] ...) ,(? scheme-ir?)) #t]
         [`(and ,(? scheme-ir?) ...) #t]
         [`(or ,(? scheme-ir?) ...) #t]
         [`(apply ,(? scheme-ir?) ,(? scheme-ir?)) #t]
         [(? (listof scheme-ir?)) #t]
         [(? prim?) #t]
         [(? symbol?) #t]
         [(? number?) #t]
         [(? boolean?) #t]
         [''() #t]
         [_ #f]))

; Interp-ce must correctly interpret any valid scheme-ir program and yield the same value
; as DrRacket, except for closures which must be represented as `(closure ,lambda ,environment).
; (+ 1 2) can return 3 and (cons 1 (cons 2 '())) can yield '(1 2). For programs that result in a 
; runtime error, you should return `(error ,message)---giving some reasonable string error message.
; Handling errors and some trickier cases will give bonus points. 
(define (interp-ce exp)
  ; Might add helpers or other code here...
  (define (prim? s) (member s '(+ - * = equal? list cons car cdr null?)))
  (define (interp exp env)
    (match exp
      [`(lambda ,args ,body)`(closure ,exp ,env)]
      [`(let ([,xs ,es] ... ), bdy)(interp-ce bdy (foldl (λ (x e env+)(hash-set env+ x e)) env xs es))]
      [`(if ,func ,x ,y)(if (interp func env) (interp x env) (interp y env))]
      [`(? symbol? ,x)(hash-ref env x)]
      [`(lambda (,x) ,bdy)(lambda (v) (interp-ce bdy (hash-set env x v)))]
      ;[`(,ef ,ea ...)((interp ef env)(interp ea env))]

      ;and
      [`(and) (and)]
      [`(and ,x ,y) (and (interp x env)(interp y env))]
      [`(and ,x ,y ...) (and (interp x env) (interp (append '(and) y) env))]

      ;or
      [`(or) (or)]
      [`(or ,x ,y) (or (interp x env)(interp y env))]
      [`(or ,x ,y ...) (or (interp x env) (interp (append '(or) y) env))]

      
      ;apply
      [`(apply ,op ,lst)(apply (interp op env)(interp lst env))]

      ;map
      [`(map ,op ,lst)(map (interp op env)(interp lst env))]
      
      [(? boolean? x) x]
      [(? number? x) x]
      [''() '()]
      
      ;primitive operations
      [`(,(? prim? x)) (eval x (make-base-namespace))]
      [`(,(? prim? x) ,y ,z) ((eval x (make-base-namespace)) (interp y env) (interp z env))]
      [`(,(? prim? x) ,y ...) ((eval x (make-base-namespace)) (interp y env))]
      
      ; Untagged application case goes after all other forms
      [`(,ef) (interp ef env)]
      [`(,ef ,ea) (interp ef env)(interp ea env)]
      [`(,ef ,ea ...)((interp ef env)(interp ea env))]

      [_ #f]
      ))
  (interp exp (hash)))


;(interp-ce '(let ([x 5] [y 7] [z 9])(let ([y x] [x y])(let ([z (- z y)])(+ x y z)))))
;(interp-ce '(list 1 2 3 4))
;(interp-ce '(+ 1 (apply * (list 1 2 3 4 5))))