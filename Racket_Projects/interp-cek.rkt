#lang racket


(provide interp-CEK)


; Define interp-cek, a tail recursive (small-step) interpreter for the language:
;;;  e ::= (lambda (x) e)
;;;      | (e e)
;;;      | x
;;;      | (let ([x e]) e)   
;;;      | (call/cc e) 
;;;      | (if e e e)
;;;      | (and e e)
;;;      | (or e e)
;;;      | (not e)
;;;      | b
;;;  x ::= < any variable satisfying symbol? >
;;;  b ::= #t | #f

; You can use (error ...) to handle errors, but will only be tested on
; on correct inputs. The language should be evaluated as would the same subset 
; of Scheme/Racket. In order to implement call/cc properly, your interpreter
; must implement a stack (as opposed to using Racket's stack by making the
; interpreter directly recursive) yourself and then allow whole stacks to be
; used as first-class values, captured via the call/cc form. Because your 
; interpreter implements its own stack, it does not use Racket's stack,
; and so every call to interp-CEK must be in tail position!
; Use symbol 'halt for an initial, empty stack. When a value is returned
; to the 'halt continuation, that value is finally returned from interp-CEK.
; For first-class continuations, use a tagged `(kont ,k) form where k is the
; stack, just as in the CE interpreter you used a tagged `(closure ,lam ,env)
; form for representing closures.

; For example:
;;; (interp-CEK `(call/cc (lambda (k) (and (k #t) #f))) (hash) 'halt)
; should yield a value equal? to #t, and
;;; (interp-CEK `(call/cc (lambda (k0) ((call/cc (lambda (k1) (k0 k1))) #f))) (hash) 'halt)
; should yield a value equal? to `(kont (ar #f ,(hash 'k0 '(kont halt)) halt))


(define (interp-CEK cexp [env (hash)] [kont 'halt])
  (define (return kont v)
    (match kont
      [`(ar ,arg ,env ,kont) (interp-CEK arg env `(fn ,v ,kont))]
      [`(fn (closure (lambda (,x) ,body) ,env) ,kont) (interp-CEK body (hash-set env x v) kont)]
      [`(fn (kont ,k) ,kont)(return k v)]
      ['halt v]
      ))
  (match cexp
    [`(lambda (,x) ,body)(return kont `(closure ,cexp ,env))]
    [`(let([,x ,rhs]) ,body) (interp-CEK  `(lambda (x) ,body)) (hash-set env x rhs)]
    [`(if ,exp ,then ,else) (if (interp-CEK exp env)(interp-CEK then env)(interp-CEK else env))]
    [`(and ,x ,y) (and (interp-CEK x env) (interp-CEK y env))]
    [`(or ,x ,y) (or (interp-CEK x env)(interp-CEK y env))]
    [`(not ,x) (not (interp-CEK x env))]
    [`(call/cc (,x ,y)) (interp-CEK `(call/cc ,x) env)(interp-CEK `(call/cc ,y) env)]
    [`(call/cc (lambda (,cc) ,body))(interp-CEK body (hash-set env cc `(kont ,kont)) kont)]
    [(? symbol? x) (return kont (hash-ref env x))]
    [(? boolean? x) (return kont x)]
    [`(,funct ,arg) (interp-CEK funct env `(ar ,arg ,env ,kont))]
    ))

(interp-CEK '(call/cc(lambda (top)(let ([cc (call/cc (lambda (cc) (cc cc)))])(if (call/cc (lambda (k) (if (cc (lambda (x) (top #f))) (k #f) (k #f))))#t #t)))))