(ns line-web-scraper.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [line-web-scraper.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[line-web-scraper started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[line-web-scraper has shut down successfully]=-"))
   :middleware wrap-dev})
