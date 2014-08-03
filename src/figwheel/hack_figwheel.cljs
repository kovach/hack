(ns hack-figwheel
  (:require [figwheel.client :as fw :include-macros true]))

(fw/watch-and-reload
  :websocket-url "ws://cutfree.net/hack/figwheel-ws")
