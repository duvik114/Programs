(definterface OperationInterface (evaluate [collection]) (differ [vaar]))

(deftype BinOperation [f opSign f_diff args]
  Object
  (toString [this] (str "(" opSign (apply str (map #(str " " (.toString %)) args)) ")"))
  OperationInterface
  (evaluate [this collection] (apply f (mapv #(.evaluate % collection) args)))
  (differ [this vaar] (f_diff (mapv #(.differ % vaar) args) args)))
(deftype UnOperation [f opSign f_diff arg]
  Object
  (toString [this] (str "(" opSign " " (.toString arg) ")"))
  OperationInterface
  (evaluate [this collection] (f (.evaluate arg collection)))
  (differ [this vaar] (f_diff (.differ arg vaar) arg)))
(deftype Const [c]
  Object
  (toString [this] (format "%.1f" (double c)))
  OperationInterface
  (evaluate [this _] c)
  (differ [this vaar] (Const. 0)))
(deftype Var [key]
  Object
  (toString [this] key)
  OperationInterface
  (evaluate [this collection] (get collection key))
  (differ [this vaar] (cond
                        (= (.toString vaar) key) (Const. 1)
                        :else (Const. 0))))

(defn Variable [key] (Var. key))
(defn Constant [c] (Const. c))
(defn Add [& args] (BinOperation. + "+" (fn [dargs _] (apply Add dargs)) args))
(defn Subtract [& args] (BinOperation. - "-" (fn [dargs _] (apply Subtract dargs)) args))
(defn Multiply [& args] (BinOperation. * "*" (fn [[darg1 darg2] [arg1 arg2]] (Add (Multiply darg1 arg2) (Multiply darg2 arg1))) args))
(defn Divide [& args] (BinOperation. (fn [a, b] (/ (double a) (double b))) "/"                                         ;
                                     (fn [[darg1 darg2] [arg1 arg2]] (Divide (Subtract (Multiply darg1 arg2) (Multiply darg2 arg1)) (Multiply arg2 arg2))) args))
(defn Negate [key] (UnOperation. - "negate" (fn [darg _] (Negate darg)) key))
(declare Cos)
(defn Sin [key] (UnOperation. #(Math/sin %) "sin" (fn [darg arg] (Multiply (Cos arg) darg)) key))
(defn Cos [key] (UnOperation. #(Math/cos %) "cos" (fn [darg arg] (Multiply (Negate (Sin arg)) darg)) key))

(def maap {
           '+ Add
           '- Subtract
           '* Multiply
           '/ Divide
           'negate Negate
           'sin Sin
           'cos Cos
           })
(defn parseObject [expr] (cond
                           (string? expr) (parseObject (read-string expr))
                           (symbol? expr) (Variable (str expr))
                           (number? expr) (Constant expr)
                           (list? expr) (apply (get maap (nth expr 0)) (mapv parseObject (drop 1 expr)))))

(defn evaluate [exp collection] (.evaluate exp collection))
(defn toString [exp] (.toString exp))
(defn diff [exp vaar] (.differ exp (parseObject vaar)))
