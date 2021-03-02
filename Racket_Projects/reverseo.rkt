#lang racket

(provide reverseo)
(provide appendo)
(require "mk.rkt")

;; Example: an appendo function for relational list append
(define (appendo lst0 lst1 full)
  (conde ; conde is disjunction: either case in square brackets must be true
   [(== lst0 '())
    ; this case says: lst0 is unified (matched) with '(),
    ; and full is unified with lst1
    (== full lst1)]
   [(fresh (first rest tail)
           ; we introduce three new fresh (initially unconstrained variables)
           ; this case says: lst0 deomposes into (cons first rest),
           (== lst0 (cons first rest))
           ; full decomposes into (const first tail)
           (== full (cons first tail))
           ; and rest appended to lst1 matches tail
           (appendo rest lst1 tail))]))


; Your task is to write a (list) reversing relation: 
(define (reverseo lst0 lst1)
  (conde
   [(== '() lst0)(== '() lst1)]
   [(fresh (first rest tail)
           (== lst0 (cons first rest))
           ;(== lst1 (cons first rest))
           ;(reverseo rest lst1))]
           (appendo tail (cons first '()) lst1)(reverseo rest tail))]
   ))

(define my-appendo
  (λ (lst lst0 out)
    (conde
     [(== '() lst)(== lst0 out)]
     [(fresh (first mid res)
             (== lst (cons first mid))
             (my-appendo mid lst0 res)
             (== (cons first res) out))])))
;(run 1 (q) (my-appendo '(1 2) '(3 4) q))