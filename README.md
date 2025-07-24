# 2025-DB2Markdown
連線 PostgreSQL 即可匯出 DB 文件



## 開發筆記

### Profile 設定

預設可以不帶 `profile` 參數，但建議依情況使用 `profile` 參數來指定設定檔：

- `dev` 是指定本地開發用的設定檔。
- `sit` 是指定系統整合測試環境的設定檔。
- `uat` 是指定用戶驗收測試環境的設定檔。
- `prod` 是指定正式環境的設定檔。

### application.properties 動態參數

properties 設定檔使用帶入環境變數作為參數，例如：

- ${DB_USERNAME} 從環境變數中讀取 `DB_USERNAME` 的值。
- ${DB_PASSWORD} 從環境變數中讀取 `DB_PASSWORD` 的值。

依據不同啟動環境做設定，例如 :

- IntelliJ IDEA 有 Environment Variables 可以設定。