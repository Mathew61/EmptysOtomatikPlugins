(ns rocks.empty.otomatik-plugins.dad-plugin
  (:require [rocks.empty.otomatik.irc-commands :as irc-commands]))

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
        writer (:writer (:connection packet))
        room (nth (:params (:message packet)) 0)
        bot-nick (:nickname packet)
        sender (first (:prefix (:message packet)))
        response-location (if (= room bot-nick) sender room)
        msg (str "Hi " (nth results 2) ", I'm " bot-nick ".")
        ]
        (str "PRIVMSG " response-location " :" msg)))))

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
