(defproject emptys-otomatik-plugins "0.2.0-SNAPSHOT" ; version should match irc-bot's version
    :url "https://github.com/TheEmpty/EmptysOtomatikPlugins"
    :license {:name "Eclipse Public License"
              :url "http://www.eclipse.org/legal/epl-v10.html"}
    :dependencies [
                   [org.clojure/clojure "1.8.0"]
                   [otomatik "0.2.0-SNAPSHOT"]
                   [org.clojure/core.async "0.2.395"]
                  ]
    :target-path "target/%s"
    :profiles {:uberjar {:aot :all}})
