(def project 'seancorfield/boot-localrepo)
(def version "0.1.0")

(set-env! :resource-paths #{"src"}
          :dependencies   '[[org.clojure/clojure "RELEASE" :scope "provided"]
                            [leiningen "RELEASE"
                             :exclusions [org.clojure/clojure]]
                            [lein-localrepo "RELEASE"
                             :exclusions [org.clojure/clojure]]
                            [boot/core "RELEASE" :scope "test"]])

(task-options!
 pom {:project     project
      :version     version
      :description "A Boot task to work with your local Maven repository."
      :url         "https://github.com/seancorfield/boot-localrepo"
      :scm         {:url "https://github.com/seancorfield/boot-localrepo"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))

(deftask deploy
  "Build and deploy the project"
  []
  (comp (pom) (jar) (push)))

(require '[boot-localrepo.core :refer [coords install-artifact list-artifacts]])
