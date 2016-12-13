(ns rocks.empty.otomatik-plugins.packet-dumper-plugin)

(def registration
  {
   :author "@the_empty on GitHub"
   :function (fn [packet] (println packet) nil)
   })
