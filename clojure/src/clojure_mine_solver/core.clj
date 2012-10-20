(ns clojure-mine-solver.core
  (:use clojure.test)
  )

(def servername "http://localhost:1337/") ; Put the servername here
(def player-id "428711") ; Put your id here

(with-test
  (defn parse-result [result]
    (let [parts (clojure.string/split result #",")]
    (reduce merge 
    (map (fn [part]
      (cond 
        (.startsWith part "X=") {:x (Integer. (.substring part 2))}
        (.startsWith part "Y=") {:y (Integer. (.substring part 2))}
        (.startsWith part "result=") {:result (Integer. (.substring part 7))}
        :else {:unknown part})
  ) parts))
  ))
  (is (= {:x 5 :y 3 :result 4} (parse-result "Y=3,X=5,result=4")))
  )

(defn hint []
  (parse-result (slurp (str servername "hint?id=" player-id)))
  )

(defn open [x y]
  (parse-result (slurp (str servername "open?id=" player-id)))
  )