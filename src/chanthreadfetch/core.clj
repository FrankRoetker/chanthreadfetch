(ns chanthreadfetch.core
  (:gen-class)
  (:use chanthreadfetch.downloader)
  (:require [clojure.set]))

(defn find-images
  "
  Find all of the images on the given page.
  "
  [x]
  (set (re-seq #"images.4chan.org/[a-z0-9/.]+" x)))

(defn find-thumbs
  "
  Find all of the thumbs on the given page.
  "
  [x]
  (set (re-seq #"thumbs.4chan.org/[a-z0-9/.]+" x)))

(defn getFileNames
  "
  Get all of the filenames of the given urls.
  "
  [urls]
  (vec (map (fn [x] (vector (re-find #"[\w_.-]*?(?=[\?\#])|[\w_.-]*$" x) x)) urls)))

(defn get-images
  "
  Get all of the images and thumbs in a website source.
  "
  [x]
  (vec (distinct 
   	(concat (find-images x)
      			(find-thumbs x)))))

(defn -main [& args]
  (if (= args nil) 
   	(println "Please provide a thread url as an argument")
  	(download-all
   		(getFileNames
   			(get-images
      			(website-string (nth args 0)))))))








