(ns line-web-scraper.scrape.api
  (:require
   [etaoin.api :as api]
   [etaoin.keys :as k]
   [clojure.string :as string]
   [clojure.tools.logging :as log]))

(defn- exist-lp-tag
  "freecoins-num is as the form of 11792"
  [dr freecoins-num]
  (some #(string/includes? % (str "FREECOINS_" freecoins-num))
        (map #(api/get-element-inner-html-el dr %)
             (api/query-all dr {:xpath ".//script"}))))

(defn- exist-lfc5
  [dr]
  (some #(= % "https://freecoins.line-apps.com/lfc5.js")
        (map #(api/get-element-attr-el dr % "src")
             (api/query-all dr {:xpath ".//script"}))))

(def wait-seconds 3)

(def driver (api/chrome-headless))

(defn- map-lp-checkers-to-url [dr url tag-ck script-ck]
  (log/info "begin to scrape " url)
  (api/go dr url)
  (api/wait wait-seconds)
  (let [d1 (tag-ck dr)
        d2 (script-ck dr)]
    {:exist-lp-tag d1
     :exist-lfc5 d2}))

(defn check-lp-tag
  " dr stands for chrome-headless driver
    url stands for the target url
    f-number stands for lp freecoins number"
  [dr url f-number]
  (map-lp-checkers-to-url
   dr url #(exist-lp-tag % f-number) exist-lfc5))

(def t-url "https://www.obdesign.com.tw/inpage.aspx?no=109561")
(def q-url "https://www.footer.com.tw/?utm_source=LINE&utm_medium=CPA&utm_campaign=CPA")

(check-lp-tag driver t-url 11792)
(check-lp-tag driver q-url 19172)
