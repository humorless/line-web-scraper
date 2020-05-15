(ns line-web-scraper.scrape.api
  (:require
   [etaoin.api :as api]
   [etaoin.keys :as k]
   [clojure.string :as string]
   [clojure.tools.logging :as log]))

(defn- exist-lp-tag
  [dr]
  (filter #(string/includes? % "FREECOINS_")
          (map #(api/get-element-inner-html-el dr %)
               (api/query-all dr {:xpath ".//script"}))))

(defn- exist-lfc5
  [dr]
  (some #(= % "https://freecoins.line-apps.com/lfc5.js")
        (map #(api/get-element-attr-el dr % "src")
             (api/query-all dr {:xpath ".//script"}))))

(defn- map-lp-checkers-to-url
  [dr url tag-ck script-ck wait-seconds]
  (log/info "scrape " url)
  (api/go dr url)
  (api/wait wait-seconds)
  (let [d1 (tag-ck dr)
        d2 (script-ck dr)]
    (log/info ":exist-lp-tag: " d1)
    (log/info ":exist-lfc5: " d2)
    {:exist-lp-tag d1
     :exist-lfc5 d2}))

(defn check-lp-tag
  " dr stands for chrome-headless driver
    url stands for the target url"
  ([url]
   (check-lp-tag url 1))
  ([url wait-seconds]
   (api/with-chrome-headless nil driver
     (map-lp-checkers-to-url
      driver url exist-lp-tag exist-lfc5 wait-seconds))))
