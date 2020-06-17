(ns line-web-scraper.middleware
  (:require
   [line-web-scraper.env :refer [defaults]]
   [line-web-scraper.config :refer [env]]
   [ring-ttl-session.core :refer [ttl-memory-store]]
   [ring.middleware.cors :refer [wrap-cors]]
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]))

(defn wrap-base [handler]
  (-> ((:middleware defaults) handler)
      (wrap-cors :access-control-allow-origin [(re-pattern (:cors-regex-str env))]
                 :access-control-allow-methods [:get :put :post :delete])
      (wrap-defaults
       (-> site-defaults
           (assoc-in [:security :anti-forgery] false)
           (assoc-in  [:session :store] (ttl-memory-store (* 60 30)))))))
