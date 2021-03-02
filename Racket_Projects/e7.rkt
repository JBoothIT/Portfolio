#lang racket

;; Exercises 7: Lazy streams and generators
(provide stream-first
         stream-rest
         stream-ref
         stream-take
         fibonacci-stream
         factorial-stream
         fibonacci-generator
         factorial-generator
         stream->generator)

; Example: A lazy stream for fibonacci sequence
; (A stream represents an infinite sequence as a pair of a head element 
; with a lazy-stream-tail, wrapped in a thunk to delay evaluation)
(define (fibonacci-tail a b)
  (cons a (lambda () (fibonacci-tail b (+ a b)))))
(define fibonacci-stream (fibonacci-tail 0 1))

; We can then define functions corresponding to first and rest, for streams:
(define stream-first car)
(define (stream-rest s) ((cdr s)))

; A utility to get the ith element
(define (stream-ref s i)
  (if (= i 0) (stream-first s) (stream-ref (stream-rest s) (- i 1))))

; A utility to compute the first i elements, and yield them as a list:
(define (stream-take s i)
  'todo)

; A lazy stream for factorial: (stream-take factorial-stream 6) => '(1 2 6 24 120 720)
(define factorial-stream 'todo)


; Time to add (set! x exp) into the mix!
; A generator is very similar to a stream, but operates on a different principle. A generator is a thunk that,
; by maintaining some internal mutable state, returns the next value of its sequence each time it's applied.
; E.g., (factorial-generator) => 1
;       (factorial-generator) => 2
;       (factorial-generator) -> 6
; Applying a generator twice will not necessarily yield the same value!
(define factorial-generator
  (let ([n 1]
        [i 2])
    (lambda ()
      (define v n)
      (set! n (* n i))
      (set! i (+ i 1))
      v)))

; E.g., (map (lambda (_) (fibonacci-generator)) '(0 0 0 0 0 0)) => '(0 1 1 2 3 5)
(define fibonacci-generator
  (let ([a 0]
        [b 1])
    'todo))

; A function that takes a stream and yields a generator for the same sequence
(define (stream->generator s)
  'todo)

