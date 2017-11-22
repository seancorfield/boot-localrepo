(ns boot-localrepo.core
  "Wrapper tasks for lein-localrepo."
  {:boot/export-tasks true}
  (:require [boot.core :as boot :refer [deftask]]
            [boot.util :as util]
            [leiningen.localrepo :refer [localrepo]]))

(defn- run-command
  "Given a localrepo command and a sequence of arguments (strings),
  invoke the main localrepo function with those arguments."
  [command args]
  (apply localrepo nil command args))

(deftask coords
  "Guess the Boot (Maven) coordinates of a file"
  [f file PATH str "the path to the JAR file."]
  (when-not file
    (util/exit-error (util/fail "-f --file is required for coords")))
  (run-command "coords" [file]))

(deftask install-artifact
  "Install artifact to local repository

  -r --repo defaults to ~/.m2/repository

  If -p is omitted, a minimal pom.xml file will be generated.

  You can either specify -a and -g or -P on its own.
  -P foo/bar is equivalent to -g foo -a bar
  If -a is specified but -g is omitted, the artifact ID will also
  be used for the group ID."
  [f file     PATH     str "the path to the JAR file."
   a artifact ARTIFACT str "the artifact ID to use"
   g group    GROUP    str "the group ID to use"
   P project  PROJECT  str "the group/artifact ID to use"
   p pom      POM      str "the path to the pom.xml file"
   r repo     PATH     str "the path to the local repository"
   v version  VERSION  str "the version to use"]
  (let [artifact-id (cond (and artifact group (not project))
                          (str group "/" artifact)
                          (and artifact (not project))
                          (str artifact "/" artifact)
                          (and project (not artifact) (not group))
                          project
                          :else
                          "illegal")]
    (when-not (and file artifact-id version)
      (util/exit-error
       (util/fail "-f --file and -v --version are required for install,\n")
       (util/fail "along with at least one of -a --artifact or -P --project")))
    (run-command "install" (cond-> [file artifact-id version]
                             pom  (into ["-p" pom])
                             repo (into ["-r" repo])))))

(deftask list-artifacts
  "List artifacts in local repository"
  [d details      bool "detailed output"
   f filenames    bool "Show filenames of artifacts"
   s descriptions bool "Show descriptions of projects"
   r repo    PATH str  "the path to the local repository"]
  (when (< 1 (count (keep identity [details filenames descriptions])))
    (util/exit-error
     (util/fail "Only one of -d --details, -f --filenames, ")
     (util/fail "or -s --descriptions may be selected")))
  (run-command "list" (cond-> []
                        details      (into ["-d"])
                        filenames    (into ["-f"])
                        descriptions (into ["-s"])
                        repo         (into ["-r" repo]))))
