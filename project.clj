(defproject emptys-otomatik-plugins "0.2.0-SNAPSHOT" ; version should match irc-bot's version
    :url "https://github.com/TheEmpty/EmptysOtomatikPlugins"
    :license {:name "MIT License"
              :url "http://www.opensource.org/licenses/mit-license.php"}
    :dependencies [
                   [org.clojure/clojure "1.8.0"]
                   [otomatik "0.2.0-SNAPSHOT"]
                  ]
    :target-path "target/%s"
    :profiles {:uberjar {:aot :all}})
