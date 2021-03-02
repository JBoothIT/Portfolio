#lang racket

;; Exercises 3: power-set and inclusion-exclusion principle

(provide power-set
         total-rect-area)

; power-set: takes an arbitrary Racket (set) and returns its power-set, the set of all its subsets
(define (power-set st)
  (let ([x (set->list st)])
        (list->set(combinations x))))

; total-rect-area: takes a list of rectangles (defined in e2) and returns the total covered area
; Note: do not double-count area covered by >1 rectangles
; E.g., (total-rect-area '((rect 0 0 2 2) (rect 1 1 3 3))) => 7
; Hint: use the power-set function and the inclusion-exclusion principle; review your functions from e2
(define (rect x0 y0 x1 y1)
  `(rect ,min(x1 x1) ,min(y0 y1) ,max(x0 x1) ,max(y0 y1)))

(define (total-rect-area rect-list)
  set-count(allNewL rect-list))

(define (allLX r)
  (range (car (cdr r)) (car (cdr (cdr (cdr r))))))

(define (allLY r)
  (range (car (cdr (cdr r))) (car (cdr (cdr (cdr( cdr r))))) ))
  (define (allLC r)
    (define (loop lX lY)
      (if (null? lY)
          (set)
          (set-add (loop lX (cdr lY))(cons (car lX)(car lY)))))
    (define (loop2 lX lY)
      (if(null? lX)
         (set)
         (set-union (loop2 (cdr lX) lY) (loop lX lY))))
    (loop2 (allLX r)(allLY r)))

(define (allNew r)
  (define (loop lO)
    (if(null? lO)
        (set)
        (set-add (loop (cdr lO))
                 (rect (car (car lO))
                       (cdr (car lO))
                       (add1 (car (car lO)))
                       (add1 (car (car lO)))))))
    (loop (set->list (allLC r))))

(define (allNewL l)
  (define (loop l)
    (if (null? l)
        (set)
        (set-union (loop (cdr l))(allNew (car l)))))
  (loop l))
    
    
         