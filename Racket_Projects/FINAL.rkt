#lang racket
;;Factorial Default
;(define (append lst0 lst1)
;  (if (null? lst0)
;      lst1
;      (cons (car lst0) (append (cdr lst0) lst1))
;      ))
;
;(append '(1 2 3) '(4 5))



;Y-Comb Append

;(Y f) == (f (Y f))
;Y = (lambda (f) (f (Y f)))
;Y = (U (lambda (y) (lambda (f) (f ((U y) f)))))
;Y = (U (lambda (y) (lambda (f) (f (lambda (x) (((U y) f) x))))))

(let* ([U-comb (lambda (x) (x x))]
       [y-append (U-comb (lambda (my-append)
                      (lambda (lst1 lst2)
                        (if (null? lst1)
                            lst2
                            (cons (car lst1) ((U-comb my-append) (cdr lst1) lst2))))))])
  (pretty-print (y-append '(1 2 3) '(4 5))))

;Y-comb factorial
(let* ([U-comb (lambda (x) (x x))]
       [y-fact (U-comb (lambda (my-fact)
                         (lambda (n)
                           (if (= n 0)
                               1
                               (* n ((U-comb my-fact)(- n 1)))))))])
  (pretty-print (y-fact 5)))


(let* ([U-comb (lambda (x) (x x))]
       [Y-comb (U-comb (lambda (y) (lambda (f) (f (lambda (x) (((U-comb y) f) x))))))]
       [my-fact (Y-comb (lambda (my-fact)
                          (lambda (n)
                            (if (= n 0)
                                1
                                (* n (my-fact (- n 1)))))))])
(pretty-print (my-fact 5)))



;Map functions
(define (map-recur op lst)
  (if (empty? lst)
      '()
      (cons (op (car lst)) (map-recur op (cdr lst)))))
;(map-recur add1 '(1 2 3 4))


(define (map-fold op lst)
  (foldr (lambda (x end) (cons (op x) end))
         '() lst))
;(map-fold add1 '(1 2 3 4))




;map - stack passing
(define (map-ST op lst)
  (define (tailMap op lst stack)
    (if (empty? lst)
        (ret op '() stack)
        (tailMap op (cdr lst) `((map ,(car lst)) . ,stack))))
   (define (ret op v stack)
    (match stack
      ['() v]
      [`((map ,val) ,stack ...)(ret op (cons (op val) v) stack)]
    ))
    (tailMap op lst '()))
(map-ST add1 '(1 2 3 4))
