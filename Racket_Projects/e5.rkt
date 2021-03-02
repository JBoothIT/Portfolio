#lang racket

;; Exercises 5: free variables and capture avoiding substitution

(provide exp?
         free-vars
         capt-avoid-subst)

; A predicate for terms in the lambda calculus
(define (exp? e)
  (match e
         [(? symbol?) #t]
         [`(,(? exp?) ,(? exp?)) #t]
         [`(lambda (,(? symbol?)) ,(? exp?)) #t]
         [_ #f]))

; Takes an arbitrary exp? and returns a set of its free variables
; E.g., (free-vars '(lambda (x) (f (g x)))) => (set 'f 'g)
(define (free-vars exp)
  (match exp
    [(? symbol? var) (set var)]
    [`(,e0 ,e1)(set-union(free-vars e0) (free-vars e1))]
    [`(lambda (,x) ,e0)(set-remove (free-vars e0) x)]
    ))

; Capture avoiding substitution:
; Takes an expression e0, and returns it, except with every instance of x within replaced with e1: 
;   i.e, e0[x <- e1]
; However, you must care to avoid capturing a variable incorrectly, recall that, via beta reduction:
;   ((lambda (y) (lambda (y) y)) (lambda (x) x))  -->beta  (lambda (y) y)   --- inner y shadows the outer
; If a substitution is not permitted (i.e., because it would first require an administrative 
; renaming---an alpha-reduction), return 'failed instead
;   E.g., (capt-avoid-subst ) => 'failed
(define (capt-avoid-subst e0 x e1)
  (define e1free (free-vars e1))
  (define (subst e)
    (match e
      [(? symbol? y) #:when(equal? y x) e1]
      [(? symbol? y)y]
      [`(lambda (,y) ,body) #:when (set-member? e1free y)'failed]
      [`(lambda (,y) ,body) #:when (not(equal? x y))`(lambda (,y) ,(subst body))]
      [`(lambda (,y) ,body)`(lambda (,y),body)]
      [`(,body1 ,body2)(map subst e)]
      ))
  (subst e0))
