(ns line-web-scraper.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[line-web-scraper started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[line-web-scraper has shut down successfully]=-"))
   :middleware identity})
