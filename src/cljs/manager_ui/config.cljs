(ns manager-ui.config)

(def debug?
  ^boolean goog.DEBUG)

; (goog-define URL "http://0.0.0.0:8000")
(goog-define URL "NGINX_REPLACE_API_URL")
; (goog-define TELEGRAM_ID "NGINX_REPLACE_TELEGRAM_ID")

(def local-store-auth-key "auth")
