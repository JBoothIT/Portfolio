#lang racket

;; Assignment 0: complete three simple functions

(provide implies-value
         median-of-three
         point-distance)


; Compute the truth value of the proposition "x --> y" where x and y are booleans
(define (implies-value x y)
  (if (and (equal? x #t)(equal? y #f)) #f #t))

; Compute the median of three integers
(define (median-of-three x y z)
  (car (cdr (sort (list x y z) <))))

; Compute the distance between two (x,y) pairs of integers
(define (point-distance x0 y0 x1 y1)
  (sqrt (+ (sqr(- x1 x0))(sqr (- y1 y0)))))




