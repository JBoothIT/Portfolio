#lang racket

;; Exercises 2: rectangle library

(provide rect
         rect?
         rect-area
         rect-intersect
         rect-list-intersect
         all-sub-rectangles)

; any two opposing corners of a grid-aligned rectangle as pairs (x0,y0), (x1,y1)
; --> `(rect ,lower-left-x ,lower-left-y ,upper-right-x ,upper-right-y) 
(define (rect x0 y0 x1 y1)
  ; return a normalized rect-tagged s-expr representation of the rectangle
  `(rect ,x1 ,y0 ,x0 ,y1))

(define (rectA x0 y0 x1 y1)
  `(rect ,x0 ,y0 ,x1 ,y1))

; Predicate defining a rectangle
(define (rect? r)
  (match r
         [`(rect ,x0 ,y0 ,x1 ,y1)
          (and (andmap integer? `(,x0 ,x1 ,y0 ,y1))
               (<= x0 x1)
               (<= y0 y1))]
         [else #f]))

; Given a rect?, yield its (integer?) area
(define (rect-area rect)
  (* (- (match  (cdr rect) [(list a _ _ _) a])
        (match  (cdr rect) [(list _ _ a _) a]))
     (- (match  (cdr rect) [(list _ _ _ a) a])
        (match  (cdr rect) [(list _ a _ _) a]))
     ))

; Compute the rectangular intersection of any two rectangles
; If there is no intersection, return a rectangle with 0 area.
(define (rect-intersect rect0 rect1)
  (rect (min (match (cdr rect0) [(list x1 _ _ _) x1]) (match  (cdr rect1) [(list x1 _ _ _) x1]))
        (min (match (cdr rect0) [(list _ y0 _ _) y0]) (match  (cdr rect1) [(list _ y0 _ _) y0]))
        (max (match (cdr rect0) [(list _ _ x0 _) x0]) (match  (cdr rect1) [(list _ _ x0 _) x0]))
        (min (match (cdr rect0) [(list _ y0 _ _) y0]) (match  (cdr rect1) [(list _ _ _ y1) y1]))
  ))

; Compute the intersection of a list of one or more rectangles
; E.g., the list `((rect 0 0 10 10) (rect 0 -5 10 1) (rect -5 -5 2 5))
;       has intersection `(rect 0 0 2 1)
(define (rect-list-intersect rect-list)
  (match rect-list
      [`(,var0, var1 . ,nlst) (rl-int-h nlst (rect-list-intersect-hh var0 var1) '() )]))

(define (rl-int-h rect-list nlst lst)
  (if (null? rect-list)
      nlst
      (match rect-list
        [`(,var0 . ,lst) (rl-int-h lst (rect-list-intersect-hh nlst var0)'())])))

(define (rect-list-intersect-hh rect0 rect1)
  (rectA(max (match (cdr rect0) [(list x1 _ _ _) x1]) (match  (cdr rect1) [(list x1 _ _ _) x1]))
        (max (match (cdr rect0) [(list _ y0 _ _) y0]) (match  (cdr rect1) [(list _ y0 _ _) y0]))
        (min (match (cdr rect0) [(list _ _ x0 _) x0]) (match  (cdr rect1) [(list _ _ x0 _) x0]))
        (min (match (cdr rect0) [(list _ _ _ y1) y1]) (match  (cdr rect1) [(list _ _ _ y1) y1]))
  ))

; Compute a Racket (set) of all sub-rectangles in the given rectangle
; We will call any rectangle r', with integer side-lengths of at least 1, a "sub-rectangle" of r iff r fully contains r'
; E.g., (all-sub-rectangles (rect 0 0 0 0)) => (set)
; E.g., (all-sub-rectangles (rect 0 0 1 1)) => (set `(rect 0 0 1 1))
; E.g., (all--sub-rectangles (rect 10 5 11 7)) => (set `(rect 10 5 11 7) `(rect 10 5 11 6) `(rect 10 6 11 7))
; Hint: can you solve this using the `foldl` and `range` functions?
(define (all-sub-rectangles r)
  'todo)