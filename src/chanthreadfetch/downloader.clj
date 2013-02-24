(ns chanthreadfetch.downloader
  (:require [http.async.client :as c]
            [clojure.java.io :as io]
            [clj-http.client :as c2]))

(def defaultSaveLocation ".\\Threads\\")

(defn savelocation
  "
  The save location for the downloaded files.
  "
  [file-name]
  (str defaultSaveLocation file-name))

(defn download-image 
  "
  Downloads an image of the given url.
  "
  [filename url]
	(with-open [w (io/output-stream (savelocation filename))]
		(.write w (:body (c2/get (str "http://" url) {:as :byte-array})))))

(defn download-all
  "
  Given a vector of [file-name url]'s, download all files.
	"
  [images]
  (doseq [image images]
    (let [file-name (get image 0) url (get image 1)]
      (download-image file-name url))))

(defn website-string
  "
  Return the source of a given website URL.
  "
  [x]
  (with-open [client (c/create-client)]
  	(let [response (c/GET client x)
          status (c/status response)
        	headers (c/headers response)]
      ;(println (:code status))
 			(c/await response)
 			(c/string response))))