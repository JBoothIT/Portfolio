#lang racket

;; Assignment 1: Implementing PageRank
;;
;; PageRank is a popular graph algorithm used for information
;; retrieval and was first popularized as an algorithm powering
;; the Google search engine. Details of the PageRank algorithm will be
;; discussed in class. Here, you will implement several functions that
;; implement the PageRank algorithm in Racket.
;;
;; Hints: 
;; 
;; - For this assignment, you may assume that no graph will include
;; any "self-links" (pages that link to themselves) and that each page
;; will link to at least one other page.
;;
;; - you can use the code in `testing-facilities.rkt` to help generate
;; test input graphs for the project. The test suite was generated
;; using those functions.
;;
;; - You may want to define "helper functions" to break up complicated
;; function definitions.

(provide graph?
         pagerank?
         num-pages
         num-links
         get-backlinks
         mk-initial-pagerank
         step-pagerank
         iterate-pagerank-until
         rank-pages)

;; This program accepts graphs as input. Graphs are represented as a
;; list of links, where each link is a list `(,src ,dst) that signals
;; page src links to page dst.
;; (-> any? boolean?)
(define (graph? glst)
  (and (list? glst)
       (andmap
        (lambda (element)
          (match element
                 [`(,(? symbol? src) ,(? symbol? dst)) #t]
                 [else #f]))
        glst)))

;; Our implementation takes input graphs and turns them into
;; PageRanks. A PageRank is a Racket hash-map that maps pages (each 
;; represented as a Racket symbol) to their corresponding weights,
;; where those weights must sum to 1 (over the whole map).
;; A PageRank encodes a discrete probability distribution over pages.
;; (-> any? boolean?)
(define (pagerank? pr)
  (and (hash? pr)
       (andmap symbol? (hash-keys pr))
       (andmap rational? (hash-values pr))
       ;; All the values in the PageRank must sum to 1. I.e., the
       ;; PageRank forms a probability distribution.
       (= 1 (foldl + 0 (hash-values pr)))))

;; Takes some input graph and computes the number of pages in the
;; graph. For example, the graph '((n0 n1) (n1 n2)) has 3 pages, n0,
;; n1, and n2.
;;
;; (-> graph? nonnegative-integer?)
(define (num-pages graph)
  (if(graph? graph)
     (num-pages-h(remove-duplicates(flatten graph)))
     0
  ))

(define (num-pages-h graph)
  (if (and (null? graph) (not (nonnegative-integer? graph)))
     0
     (+ 1 (num-pages-h(rest graph)))
  ))

;; Takes some input graph and computes the number of links emanating
;; from page. For example, (num-links '((n0 n1) (n1 n0) (n0 n2)) 'n0)
;; should return 2, as 'n0 links to 'n1 and 'n2.
;;
;; (-> graph? symbol? nonnegative-integer?)
(define (num-links graph page)
  (if (graph? graph)
    (num-links-h graph page)
    0
  ))

(define (num-links-h graph page)
  (cond
    [(null? graph) 0]
    [(not (eq? page (car(car graph))))(+ 0 (num-links-h(cdr graph) page))]
    [(eq? page (car(car graph)))(+ 1 (num-links-h(cdr graph) page))]
    ))

;; Calculates a set of pages that link to page within graph. For
;; example, (get-backlinks '((n0 n1) (n1 n2) (n0 n2)) 'n2) should
;; return (set 'n0 'n1).
;;
;; (-> graph? (set/c symbol?))
(define (get-backlinks graph page)
  (define (get-backlinks-h graph nSet)
   (match graph
     ['()(list->set nSet)]
     [`((,var0 ,var1) . ,lst)
      (if (eq? var1 page)
          (get-backlinks-h lst (cons var0 nSet))
          (get-backlinks-h lst nSet))]))
  (get-backlinks-h graph '()))

;; Generate an initial pagerank for the input graph g. The returned
;; PageRank must satisfy pagerank?, and each value of the hash must be
;; equal to (/ 1 N), where N is the number of pages in the given
;; graph.
;; (-> graph? pagerank?)
(define (mk-initial-pagerank graph)
  (if (graph? graph)
      (if(pagerank? (for/hash ([i (format-list graph)])
                      (values i (/ 1 (length (format-list graph))))))
         (for/hash ([i (format-list graph)])
                    (values i (/ 1 (length (format-list graph)))))
         "error not a proper pagerank")
      "error not a graph"
  ))

(define (format-list graph)
  (remove-duplicates(flatten graph))
  )

;; Perform one step of PageRank on the specified graph. Return a new
;; PageRank with updated values after running the PageRank
;; calculation. The next iteration's PageRank is calculated as
;;
;; NextPageRank(page-i) = (1 - d) / N + d * S
;;
;; Where:
;;  + d is a specified "dampening factor." in range [0,1]; e.g., 0.85
;;  + N is the number of pages in the graph
;;  + S is the sum of P(page-j) for all page-j.
;;  + P(page-j) is CurrentPageRank(page-j)/NumLinks(page-j)
;;  + NumLinks(page-j) is the number of outbound links of page-j
;;  (i.e., the number of pages to which page-j has links).
;;
;; (-> pagerank? rational? graph? pagerank?)
(define (step-pagerank pr d graph)
  (for/hash ([i (sort(hash-keys pr)symbol<?)])
    (values i (+(/ (- 1 d)(num-pages graph))
                (step-pagerank-h pr d (set->list(get-backlinks graph i)) graph)))))
                         
(define (step-pagerank-h pr d keys graph)
  (cond
    [(null? keys) 0]
    [(not(null? keys)) (+ (* d(/ (hash-ref pr (car keys))(num-links graph (car keys))))
                          (step-pagerank-h pr d (cdr keys) graph))]
 ))

;; Iterate PageRank until the largest change in any page's rank is
;; smaller than a specified delta.
;;
;; (-> pagerank? rational? graph? rational? pagerank?)
(define (iterate-pagerank-until pr d graph delta)
  (if (< (car(sort(hash-values (step-pagerank pr d graph))<)) delta)
      (iterate-pagerank-until (step-pagerank pr d graph) d graph delta)
      (step-pagerank pr d graph)
      )
  )

;; Given a PageRank, returns the list of pages it contains in ranked
;; order as a list. You may assume that the none of the pages in the
;; pagerank have the same value (i.e., there will be no ambiguity in
;; ranking)
;;
;; (-> pagerank? (listof symbol?))
(define (rank-pages pr)
  (rank-pages-h pr (hash-keys pr) (sort(hash-values pr)<))
  )

(define (rank-pages-h pr keys vals)
    (cond
      [(null? vals) '()]
      [(null? keys) (rank-pages-h pr (hash-keys pr) vals)]
      [(eq? (hash-ref pr (car keys))(car vals)) (cons (car keys)(rank-pages-h pr (cdr keys)(cdr vals)))]
      [(not (eq? (hash-ref pr (car keys))(car vals))) (rank-pages-h pr (cdr keys) vals)]
    ))
