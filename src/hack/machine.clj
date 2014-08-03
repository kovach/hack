(ns hack.machine)

(defn push [ops1 ops2]
  (vec (concat ops1 ops2)))

(defn to-bool [b]
  {0 (if b :true :false)})

(def constraints
  '[(has-key r1 key) ; #n :foo -> ...   |-> r2. (get r1 key) = r2
    (element r1 keys) ; #n = :nil or :cons  |-> split by r1 = each key
    (eq p1 p2) ; #n :foo == #m   |-> unify?
    ]
  )

(defn bin-op0 [op [x1 x2 & stack]]
  (op x2 x1))
(defn bin-op [op [x1 x2 & stack]]
  (conj stack (op x2 x1)))

(defn do-op [history]
  (let [[[stack ops] & past] history]
    (if (seq ops)
      (let [[op & ops-tail] ops]
        (cons
          (cond
            ; dup
            (= op 'dup) [(conj stack (first stack)) ops-tail] 
            ; drop
            (= op 'drop) [(rest stack) ops-tail] 
            ; copy root
            (= op '.) [(conj stack (last stack)) ops-tail]
            ; call
            (= op '()) [(rest stack) (push (first stack) ops-tail)]
            (= op 'look) [(conj (drop 2 stack) (get (second stack) (first stack))) ops-tail]

            ; operators
            (= op '+) [(bin-op + stack) ops-tail]
            (= op '-) [(bin-op - stack) ops-tail]
            (= op '*) [(bin-op * stack) ops-tail]
            (= op '/) [(bin-op / stack) ops-tail]

            (= op '<) [(conj (nnext stack) (to-bool (bin-op0 < stack))) ops-tail]
            (= op '>) [(conj (nnext stack) (to-bool (bin-op0 > stack))) ops-tail]
            (= op '=) [(conj (nnext stack) (to-bool (bin-op0 = stack))) ops-tail]

            (= op 'swap) [(cons (second stack) (cons (first stack) (drop 2 stack))) ops-tail]
            (= op 'over) [(cons (second stack) stack) ops-tail]

            ; read location
            (keyword? op) 
            [(conj (rest stack) (get (first stack) op)) ops-tail]

            ; modify location
            (= op 'mod) [(let [[val path map & stack] stack]
                           (conj stack
                                 (assoc-in map path val)))
                         ops-tail]

            ; case split
            (= op 'case) [(nnext stack)
                          (push (get (first stack) (get (second stack) 0))
                                ops-tail)]


            ; Interpret 'foo' as '. :foo ()'
            (symbol? op)
            [stack (push ['. (keyword op) '()] ops-tail)]

            ; push
            :else
            [(conj stack op) ops-tail])
          history))
      history)))

(defn reduce-m [history]
  (if (empty? (second (first history)))
    history
    (reduce-m (do-op history))
    )
  )

(def stack1
  '({:sq [dup *]
     :fac [dup 1 > {:false [drop 1] :true [dup 1 - fac *]} case]
     :fib [dup 1 > {:false [] :true [dup 2 - fib swap 1 - fib +]} case]
     })
  )
(def empty-stack
  '()
  )
(-> [[stack1
      '[{:time 0} store
        ]
      ]]
    reduce-m
    result
    )
(defn result [x]
  (-> x
      first first butlast
      )
  )

