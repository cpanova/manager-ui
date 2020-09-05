(defproject manager-ui "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.764"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [thheller/shadow-cljs "2.9.3"]
                 [reagent "0.10.0"]
                 [re-frame "0.12.0"]
                 [cljs-ajax "0.8.0"]
                 [day8.re-frame/http-fx "v0.2.0"]
                 [bidi "2.1.6"]
                 [kibu/pushy "0.3.8"]
                 [venantius/accountant "0.2.5"]
                 [soda-ash "0.82.2"
                  :exclusions [cljsjs/react-dom
                               cljsjs/react
                               org.clojure/clojurescript]]
                 ; [com.andrewmcveigh/cljs-time "0.5.2"]
                 [com.cemerick/url "0.1.1"]
                 [metosin/komponentit "0.3.10"]
                 [hickory "0.7.1"]]

  :plugins [
            ; [lein-shadow "0.2.0"]

            [lein-shell "0.5.0"]]

  :min-lein-version "2.9.0"

  :source-paths ["src/clj" "src/cljs"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]


  :shell {:commands {"open" {:windows ["cmd" "/c" "start"]
                             :macosx  "open"
                             :linux   "xdg-open"}}}

  :aliases {"dev"          ["with-profile" "dev" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "watch" "app"]]
            "prod"         ["with-profile" "prod" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "release" "app"]]
            ; "dev"          ["with-profile" "dev" "do"
            ;                 ["shadow" "watch" "app"]]
            ; "prod"         ["with-profile" "prod" "do"
            ;                 ["shadow" "release" "app"]]
            "build-report" ["with-profile" "prod" "do"
                            ["shadow" "run" "shadow.cljs.build-report" "app" "target/build-report.html"]
                            ["shell" "open" "target/build-report.html"]]
            "karma"        ["with-profile" "prod" "do"
                            ["shadow" "compile" "karma-test"]
                            ["shell" "karma" "start" "--single-run" "--reporters" "junit,dots"]]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "1.0.0"]]
    :source-paths ["dev"]}

   :prod {}}



  :prep-tasks [])
