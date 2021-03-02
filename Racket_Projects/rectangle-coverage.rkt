#lang racket

;; Assignment 2: Quad-trees and immutable data-structures

(provide total-rect-area)

; total-rect-area: returns the total integer area covered by any (one or more) rectangles in the given list 
; Don't double-count. Should be identical to the same solution from e3.rkt, except this version must be in O(n log n)
; Hint: implement an immutable quad-tree to represent 2D space; FYI, a solution takes only ~30-40 lines of code.
(define (total-rect-area rect-list)
   (define (rt-add rect rt)
    (match rect
      [`(rect ,x0 ,y0, x1 ,y1) #:when(and (< x0 x1)(< y0 y1))
                  (match rt
                    ['covered 'covered]
                    ['empty `(rt ,x0 ,y0 (rt ,x1 ,y1 empty empty covered empty) empty empty empty)]
                    [`(rt ,px ,py ,q0 ,q1 ,q2 ,q3)
                     `(rt ,px ,py
                          ,(rt-add `(rect ,(max x0 px),(max y0 py),(max x1 px),(max y1 py)) q0)
                          ,(rt-add `(rect ,(min x0 px),(max y0 py),(min x1 px),(max y1 py)) q1)
                          ,(rt-add `(rect ,(min x0 px),(min y0 py),(min x1 px),(min y1 py)) q2)
                          ,(rt-add `(rect ,(max x0 px),(min y0 py),(max x1 px),(min y1 py)) q3)
                    )]
     )]  [_ rt]))
  (define (sum-quad rt [x0 -inf.0][y0 -inf.0][x1 +inf.0][y1 +inf.0])
    (match rt
      ['empty 0]
      ['covered (* (- x1 x0)
                   (- y1 y0))]
      [`(rt ,px ,py ,q0 ,q1 ,q2 ,q3)
            (+(sum-quad q0 px py x1 y1)
              (sum-quad q1 x0 py px y1)
              (sum-quad q2 x0 y0 px py)
              (sum-quad q3 px y0 x1 py))]))
  (sum-quad (foldl rt-add 'empty rect-list)))