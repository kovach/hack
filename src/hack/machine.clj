(ns hack.machine)
(def commands
  '(copy step)
  )

(when-let [[op & ops] []]
  [op ops]
  )


(defn do-op [[ops stack]]
  (if (seq ops)
    (let [[op & ops] ops]
      [ops
       (case op
        . (conj stack (first stack))
        - (rest stack)
        ; Default, interpet as name
        (let [name op
              top (first stack)]
          (conj (rest stack) (get top name)))
        )])))

(def stack1
  '({:a 0 :b 22 :c {:d :a}})
  )
(-> [['. :c '-] stack1]
  (do-op)
  (do-op)
  (do-op)
  )
