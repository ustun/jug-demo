(ns jug.core
  (:import
   (javax.swing JFrame JLabel JTextField JButton)
   (java.awt.event ActionListener)
   (java.awt GridLayout)))

(use 'clojure.pprint)
(require 'clojure.join)
(require 'clojure.set)

;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Primitive Types
;;;;;;;;;;;;;;;;;;;;;;;;;


1
1000M
"Ustun"
\a
\space
#"\d{3}"
(+ 1 1 2 3 4)
(class 1)

(class 1000M)

(class "Ustun")

(class \a)

(class #"\d{3}")

;;;;;;;;;;;;;;;;;

;; Collections

(def my-list (1 2 3))

(print my-list)

(def my-vec [1 2 3 4 5])

(print my-vec)

(nth my-vec 4)
(my-vec 4)
(my-vec 100)
(nth my-vec 100 "bulunamadi")

;;;;; Maps

(def ustun {:ad "Ustun" :soyad "Ozgur" :yas 29 :konum "Cyberpark"})

(print ustun)

(:ad ustun)

(ustun :ad)

(get ustun :ad)

(:okul ustun "bilkent")

(def insanlar #{
                {:ad "Ustun" :soyad "Ozgur" :yas 29 :konum "Cyberpark" :sehir "Ankara"}
                {:ad "Damla" :soyad "Ozgur" :yas 27 :konum "Fethiye" :sehir "Mugla"}
                {:ad "Can" :soyad "Uran" :yas 29 :konum "Cyberpark" :sehir "Ankara"}
                {:ad "Sitar" :soyad "Kortik" :yas 29 :konum "Cyberpark" :sehir "Ankara"}
                })

(map :ad insanlar)

(def plakalar {6 "Ankara" 34 "Istanbul"})

(def plakalar {6 "Ankara" 34 "Istanbul" 61 "Trabzon"})

(def plakalar #{{:plaka "06" :sehir "Ankara"}
                {:plaka "48" :sehir "Mugla"}
                {:plaka "34" :sehir "Istanbul"}})

(require 'clojure.set)
(map :sehir (clojure.set/join insanlar plakalar))

(pprint (map (juxt :ad :plaka) (clojure.set/join insanlar plakalar)))



;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;; Seq abstraction
;;;;;;;;;;;;;;;;;;;;;;;;

(print my-list)
(first my-list)

(print my-vec)
(first my-vec)

(print ustun)
(first ustun)
(second ustun)
(rest ustun)

;;;;;;;;;;;;;;;;;;;;;
;;;;;; Syntax
;;;;;;;;;;;;;;;;;;;;;

(+ 1 2 3)
(* 4 5 6)

(def x 3)
(def y (fn [x] (* x x)))

(y 4)

(if-not (zero? x) "x is not zero" "x is zero")

(macroexpand `(if-not (zero? x) "x is not zero" "x is zero"))

(def x 1)
(when (zero? x)
  (print "x is not zero")
  (print "some other output"))

(macroexpand `(when (zero? x)
                (print "x is not zero")
                (print "some other output")))


(defn tum-ad [p] (str "First name: "(:ad p) " surname: " (:soyad p)))

(tum-ad ustun)

(:soyad ustun)

(tum-ad ustun)

(def condition true)

(when condition
  (println "foo")
  (println "bar"))

(if condition (do (println "foo") (println "bar")))

;;;;;;;;;;;;;;;;;;;;;;;
;;;; Java Interop
;;;;;;;;;;;;;;;;;;;;;;

;; Temel
(. Math PI)

(new java.util.HashSet)

;; Şeker

(Math/PI)

(java.util.HashSet.)

(def x (java.util.HashSet.))

(.add x "Besiktas")
(.add x "Fenerbahce")
(.add x "Galatasaray")

(.contains x "Besiktas")
(.contains x "Galatasaray")

(def takimlar (java.util.HashSet.))

(doto takimlar
  (.add "Besiktas")
  (.add "Fenerbahce")
  (.add "Galatasaray"))

(def clojure-takimlar (set takimlar))

(clojure-takimlar "Fenerbahce")


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;; Swing Example
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn celsius []
  (let [frame (JFrame. "Celsius Converter3")
        temp-text (JTextField.)
        celsius-label (JLabel. "Celsius")
        convert-button (JButton. "Convert")
        fahrenheit-label (JLabel. "Fahrenheit")]
    (.addActionListener
     convert-button
     (reify ActionListener
       (actionPerformed
         [_ evt]
         (let [c (Double/parseDouble (.getText temp-text))]
           (.setText fahrenheit-label
                     (str (+ 32 (* 1.8 c)) " Fahrenheit"))))))
    (doto frame
      (.setLayout (GridLayout. 2 2 3 3))
      (.add temp-text)
      (.add celsius-label)
      (.add convert-button)
      (.add fahrenheit-label)
      (.setSize 400 80)
      (.setLocation 1000 80)
      (.setVisible true))))

(def my-gui (celsius))

(.setLocation my-gui 900 200)
(.setSize my-gui 400 100)

(.setVisible my-gui (not (.isVisible my-gui)))


(defn toggle-gui []
  (.setVisible my-gui (not (.isVisible my-gui))))


(defn increase-size []
  (.setSize my-gui
            (+ 10 (.width (.getSize my-gui)))
            (+ 10 (.height (.getSize my-gui)))))

(defn decrease-size []
  (.setSize my-gui
            (- (.width (.getSize my-gui)) 10)
            (- (.height (.getSize my-gui)) 10)))


(toggle-gui)

(increase-size)
(decrease-size)

(use 'clojure.java.javadoc)
(javadoc my-gui)

;;; (filter #(.startsWith (str %) "set") (map :name (:members (clojure.reflect/reflect my-gui))))



;;;;;;;;;;;;;;;;;;
;;;;;;;;; Fonksiyonel Programlama


(def sayilar (range 100))

(map (fn [x] (* 3 x)) sayilar)

(map #(* 3 %) sayilar)

(map (partial * 4) sayilar)

(defn square [x] (* x x))

(map square sayilar)

(map (comp (partial * 2) square) sayilar)

(filter #(zero? (mod % 3)) sayilar)



(defn divisible-by [mod-num y] (zero? (mod y mod-num)))

(filter (partial divisible-by 5) sayilar)

(reduce + [1 2 3 4])

(reduce * [1 2 3 4])

(reduce * (range 1 5))


(defn factorial [x]
  (reduce * (range 1 (inc x))))

(factorial 5)


(def takimlar [{:ad "Besiktas" :tarih 1903}
               {:ad "Fenerbahce" :tarih 1907}
               {:ad "Galatasaray" :tarih 1905}])

(map :ad takimlar)

(apply str (interpose \space (map :ad takimlar)))
(clojure.string/join ", " (map :ad takimlar))


;;;;;;;;;;;;;;;;;;;;;
;;;; Immutability and Mutable State
;;;;;;;;;;;;;;;;;;;;


;;; Value semantics

(= 1 1)

(= "Ustun" "Ustun")

(= [1 2 3] [1 2 3])

(= [1 2 3] (list 1 2 3))

(= {:ad "Ustun"} {:ad "Ustun"})

(def my-vec [1 2 3])

(conj my-vec 4)

my-vec

;;; Reference types for mutable data

(def x (atom my-vec))
(deref x)
(swap! x conj 4)
(deref x)
(swap! x (partial map square))
@x

(def ustun (atom {:ad "Ustun" :yas 29}))
(:yas ustun)
(:yas @ustun)
(swap! ustun assoc :yas 30)
(:yas @ustun)

;;; Multiple values
;; Software transactional memory
;;;;;;;;;;;;;;;;;;

(def savings (ref 1000.))
(def checking (ref 2000.))
(def mm (ref 7000.))

(defn transfer
  "Transaction to transfer money from one account to another."
  [from to amount]
  (dosync
   (alter from - amount)
   (alter to + amount)))


;;;;;;;;;;;;;;;
;; Java Interfaceleri ve Protokoller
;;;;;;;;;;;;;;

(defrecord Insan [ad soyad adres])

(defrecord Adres [semt sehir])

(def ustun (Insan. "Ustun" "Ozgur"
                   (Adres. "Bilkent" "Ankara")))

(pprint (supers (class ustun)))

(pprint ustun)
(:ad ustun)

(:adres ustun)

(:sehir (:adres ustun))

(-> ustun :adres :sehir)
(macroexpand `(-> ustun :adres :sehir))

(assoc-in ustun [:adres :sehir] "Istanbul")

ustun

;;;; Implementing Java Interfaces

(import java.net.FileNameMap)

(defrecord Thing [a]
  FileNameMap
  (getContentTypeFor [this fileName] (str a "-" fileName)))

(def thing (Thing. "foo"))

(instance? FileNameMap thing)

(map println (.getInterfaces Thing))

(.getContentTypeFor thing "jpg")



;;; Protokoller

(defprotocol IYuruKos
  (yuru [_])
  (kos [_]))

(pprint (supers (class IYuruKos)))

(deftype Insan [isim soyisim]
  IYuruKos
  (yuru [_] (str isim " yuruyor"))
  (kos [_] (str isim " kosuyor")))

(deftype Hayvan [isim]
  IYuruKos
  (yuru [_] (str isim " hizli yuruyor"))
  (kos [_] (str isim " hizli kosuyor")))


(yuru (Insan. "Ustun" "Ozgur"))
(kos (Insan. "Ustun" "Ozgur"))

(kos (Hayvan. "Lessie"))

;;;;;;;;;;;;;;;;
;; Bize ait olmayan siniflar da extend edilebilir!
;;;;;;;;;;;;;;;;

(extend-type
    java.lang.String
  IYuruKos
  (yuru [this] (str this " yuruyemez"))
  (kos [this] (str this " kosamaz"))
  )

(yuru "deneme")

(pprint (supers (class yuru)))


;;;; for comprehensions

(for [x (range 1 5) y (range 1 4)]
  (do
    (println "y is" y)
    (println "x is" x)
    (* x y)))

;; let bindings

(defn greet [t]
  (print "Merhaba" (:name t)))

(defn greet-2 [t]
  (let [name (:name t)]
    (print "Merhaba2" name)))


(defn greet-3 [{:keys [:name]}]
  (print "Merhaba3" name))


(greet {:name "Ustun" :surname "Ozgur"})

(greet-2 {:name "Ustun" :surname "Ozgur"})

(greet-3 {:name "Ustun" :surname "Ozgur"})

(def ^{:doc "this is some documentation for this variable"} x 3)

(doc x)

  (defn a-function "this multiplies 3 numbers"
    [x y z]
    (* x y z))

(doc a-function)


;;; (require 'clojure.reflect)
;;; (pprint (sort (set (map :name (:members (clojure.reflect/reflect clojure.lang.ISeq))))))
