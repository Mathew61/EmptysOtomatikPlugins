(ns rocks.empty.otomatik-plugins.dad-plugin)

; TODO casing
(defn go-dad
  [packet]
  (let [
    text (clojure.string/lower-case (last (:params (:message packet))))
    results (re-find (re-matcher #"(i'm|i am)\s+(.+?)([\.?!,]|$)" text))
    ]

    (if (= nil (nth results 2))
      nil
      (let [
          room (first (:params (:message packet)))
          sender (first (:prefix (:message packet)))
          response-location (if (= room (:nickname packet)) sender room)
        ]
        (str "PRIVMSG " response-location " :Hi" (nth results 2) ", I'm " (:nickname packet) ".")))))

(defmulti handle
  "Handles IRC commands based on the given IRC command."
  (fn [packet] (:command (:message packet))))

(defmethod handle "PRIVMSG" [packet] (go-dad packet))
(defmethod handle "NOTICE"  [packet] (go-dad packet))
(defmethod handle "ACTION"  [packet] (go-dad packet))

; NO-OP for unknown commands
(defmethod handle :default [packet] nil)

(def registration {
    :function (fn [packet] (handle packet))
    :author "@the_empty on GitHub"
  })
