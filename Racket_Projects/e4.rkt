#lang racket

;; Exercises 4: merge-sort and line-coverage problem

(provide merge-sort
         line-coverage)

; Use merge-sort to efficiently sort a list recursively
(define (merge-sort lst <=)
  (cond
    [(or (null? lst) (null? (cdr lst))) lst]
    [(null? (cddr lst)) (merge-lists (list (car lst)) (cdr lst) <=)]
    [#t (let ([split (ceiling (/ (length lst) 2))])
       (merge-lists (merge-sort (take lst split) <=)
                    (merge-sort (drop lst split) <=) <=))]))

(define (merge-lists lst ys orient)
  (cond
    [(null? lst) ys]
    [(null? ys) lst]
    [(orient (car lst) (car ys))
     (cons (car lst) (merge-lists (cdr lst) ys orient))]
    [#t (cons (car ys) (merge-lists lst (cdr ys) orient))]))

; Line coverage: take a list of lines, each encoded as a list `(,s ,e) where s <= e 
; and both are integers, and compute the overall amount of area covered.
; For example, (line-coverage '((4 9) (1 2) (6 12) (99 99))) => 9
(define (line-coverage lines-lst)
  (define (lt-add line lt)
    (match line
      [`(,rS ,rE) #:when (< rS rE)
                  (match lt
                    ['covered
                     'covered]
                    ['empty
                     `(lt ,rS empty (lt ,rE covered empty))]
                    [`(lt ,px ,left ,right)
                     `(lt ,px
                          ,(lt-add `(,(min rS px) ,(min rE px)) left)
                          ,(lt-add `(,(max rS px) ,(max rE px)) right ))])]
      [_ lt]))
  (define (sum-lt lt [wstart -inf.0][wend +inf.0])
    (match lt
      ['empty 0]
      ['covered (- wend wstart)]
      [`(lt ,px ,left ,right)
            (+(sum-lt left wstart px)
              (sum-lt right px wend))]))
  (sum-lt (foldl lt-add 'empty lines-lst)))

