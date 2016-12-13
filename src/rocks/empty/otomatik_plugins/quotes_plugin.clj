(ns rocks.empty.otomatik-plugins.quotes-plugin
  (:import java.io.File)
  (:import java.io.RandomAccessFile)
  (:require [rocks.empty.otomatik.irc-commands :as irc-commands]))

(defn make-file-handler
  [filename]
  {
   :filename filename
   :size (.length (new File filename))
  })

(defn seek-to-start-of-line 
  [raf loc]
  (loop [loc loc]
    (.seek raf loc)
    (if-not (= 0 loc)
      (if-not (= (byte 10) (.read raf))
        (recur (- loc 1))))))

(defn read-random-line
  [file-handler]
  (locking file-handler
    (let [
        raf (new RandomAccessFile (str (:filename file-handler)) "r")
        loc (long (* (Math/random) (:size file-handler)))
      ]

      (seek-to-start-of-line raf loc)
      (.readLine raf))))

(defmulti handle
    "Handles IRC commands based on the given IRC command."
      (fn [packet activation-string file-handler] (:command (:message packet))))

(defmethod handle "PRIVMSG"  [packet activation-string file-handler]
  (let
    [
     message (last (:params (:message packet)))
     to (first (:params (:message packet)))
    ]
    (if-not (.equals message activation-string)
      nil
      (str "PRIVMSG " to " :" (read-random-line file-handler)))))

; NO-OP for unknown commands
(defmethod handle :default [packet activation-string file-handler] nil)

(defn registration
  "Takes an activation string (eg, !quote) and a filename.
  Picks a line randomly using file-based search."
  [activation-string filename]

  {
   :function (fn [packet] (handle packet activation-string (make-file-handler filename)))
   :author "@the_empty on GitHub"
  })

