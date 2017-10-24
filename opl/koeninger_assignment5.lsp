; Robert Koeninger
; OPL, Assignment 5
;
; They are a set of utility functions toward the top (listlength, nthelement,
; and tophalf/bottomhalf) used by the search functions. The search functions
; themselves have a "help" sub-function with extra arguments to carry on
; intra-algorithm information.
;
; Examples of test queries are at the bottom. The testing code is commented
; so this file can be loaded without complication.

;;;
;;; Function Definitions
;;;

; Function (listlength list)
;
; Returns the length of the given list.
(defun listlengthhelp (l n) (cond
    ((equal (car l) nil) n)
    (t (listlengthhelp (cdr l) (+ n 1)))
))
(defun listlength (l) (listlengthhelp l 0))

; Function (nthelement list position)
;
; Returns the nth element of list, or 1-based index.
; If index is not in bounds (is less than 1 or greater than length),
; this function returns nil.
(defun nthelement (l n) (cond
    ((zerop n) nil)
    ((equal n 1) (car l))
    (t (nthelement (cdr l) (- n 1)))
))

; Functions (bottomhalf list) (tophalf list)
;
; Returns the top or bottom half of the list. With odd-length lists, top half
; returns half the list rounded down and bottom returns half the list rounded
; up. i.e. If the list length is 7, tophalf returns the first 3 elements,
; bottom half returns the last 4 elements.
(defun bottomhalfhelper (l n) (cond
    ((zerop n) l)
    (t (bottomhalfhelper (cdr l) (- n 1)))
))
(defun tophalfhelper (l n) (reverse (bottomhalfhelper (reverse l) n)))
(defun bottomhalf (l) (bottomhalfhelper l (floor (listlength l) 2)))
(defun tophalf (l) (tophalfhelper l (ceiling (listlength l) 2)))

; Function (seqsearch list element)
;
; Returns the 0-based index of the element e in the list l. If e is not in the
; list, this function returns a negative number.
(defun seqsearchhelp (l e n) (cond
    ((equal (car l) e) n)
    ((equal (car l) nil) -1)
    (t (seqsearchhelp (cdr l) e (+ n 1)))))
(defun seqsearch (l e) (seqsearchhelp l e 0))

; Function (binsearch list element)
;
; Returns the 0-based index of the given element e. If the element is not
; in the list, this function returns a negative number.
; *** Depends on the list being in acending order.
(defun binsearchhelp (l n e) (cond
    ((zerop n) -1000)
    ((equal n 1) (cond ((equal (car l) e) 0) (t -2000)))
    ((equal e (nthelement l (ceiling (/ n 2)))) (- (ceiling n 2) 1))
    ((< e (nthelement l (ceiling (/ n 2))))
	    (binsearchhelp (tophalf l) (floor (/ n 2)) e))
    ((> e (nthelement l (ceiling (/ n 2))))
	    (+ (floor (/ n 2)) (binsearchhelp (bottomhalf l) (ceiling (/ n 2)) e)))
    (t -3000)
))
(defun binsearch (l e) (binsearchhelp l (listlength l) e))

; Function (hybridsearch list element threshold)
;
; A hybridized search that uses a binary method first and then a sequential
; method once the remaining search area is of a cetain threshold size.
(defun hybridsearchhelp (l n e h) (cond
    ((zerop n) -1000)
    ((equal n 1) (cond ((equal (car l) e) 0) (t -2000)))
    ((equal e (nthelement l (ceiling n 2))) (- (ceiling n 2) 1))
    ((< e (nthelement l (ceiling n 2))) (cond
        ((>= h (listlength (tophalf l))) (seqsearch (tophalf l) e))
        (t (hybridsearchhelp (tophalf l) (floor n 2) e h))))
    ((> e (nthelement l (ceiling n 2))) (+ (floor n 2) (cond
        ((>= h (listlength (bottomhalf l))) (seqsearch (bottomhalf l) e))
        (t (hybridsearchhelp (bottomhalf l) (ceiling n 2) e h)))))
    (t -3000)
))
(defun hybridsearch (l e h) (hybridsearchhelp l (listlength l) e h))

;;;
;;; TESTING CODE
;;;
;
; I didn't know how these should be formatted, so here's a set of queries
; and the results they return.
;
; (setq list1 '(2 6 10 45 49 71 93))
; (setq list2 '(-30 -13 -4 0 23 28 41 67 80 114))
; (seqsearch list1 10)
;     returns "2"
; (seqsearch list1 56)
;     returns "-1"
; (seqsearch list2 41)
;     returns "6"
; (seqsearch list2 109)
;     returns "-1"
; (binsearch list1 71)
;     returns "5"
; (binsearch list2 25)
;     returns "-1995" ; Some negative number is returned to indicate NOT FOUND
; (hybridsearch list1 10 4)
;     returns "2"
;
; etc, etc.
