(ns hack.machine)
(def commands
  '(copy step)
  )

(when-let [[op & ops] []]
  [op ops]
  )


(defn do-op [history]
  (let [[[stack ops] & past] history]
    (if (seq ops)
      (let [[op & ops-tail] ops]
        (cons
          (case op
             ; dup
             . [(conj stack (first stack)) ops-tail]
             ; drop
             - [(rest stack) ops-tail]


             ; Default, interpet as name
             (let [name op
                   top (first stack)]
               [(conj (rest stack) (get top name)) ops-tail])
             )
           
          history))
      history
      )))

(def ptest1
  l1 (cons head (x) tail (nil))
  a (t1 a0 (t2) (t3 ))
  '[mod a t1 a0 t2 ]
  )

(def stack1
  '({:a 0 :c {:d :a}})
  )
(-> [[stack1 ['. :c :d '-]]]
    (do-op)
    (do-op)
    (do-op)
    first
    )
