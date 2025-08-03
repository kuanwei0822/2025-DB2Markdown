# 2025-DB2Markdown
連線 PostgreSQL 即可匯出 DB 文件

## 開發筆記

### SonarQube 手動檢查機制

- 在 IDE 安裝 SonarQube 工具。
- 在每次 Commit 之前對專案落實 SonarQube 檢查。
- 確保程式碼的品質、及時修正弱點。

### Profile 設定

採用嚴格指定 `profile` 參數來指定設定檔。

分為兩種不同範圍的設定檔，每次啟動時都必須指定一個 env 和一個 db 的設定檔：

1. env 設定檔

- `dev` 是指定本地開發用的設定檔。
- `sit` 是指定系統整合測試環境的設定檔。
- `uat` 是指定用戶驗收測試環境的設定檔。
- `prod` 是指定正式環境的設定檔。

2. db 設定檔
- `postgresql` 連線 PostgreSQL 用的設定檔。
- `mysql` 連線 MySQL 用的設定檔。
- `mssql` 連線 MSSQL 用的設定檔。
- `oracle` 連線 Oracle 用的設定檔。

依據不同啟動環境做設定，例如 :

- IntelliJ IDEA 有 Environment Variables 可以設定。
  ```
  dev,postgresql
  ```

#### properties 動態參數

properties 設定檔使用帶入環境變數作為參數，例如：

- ${DB_USERNAME} 從環境變數中讀取 `DB_USERNAME` 的值。
- ${DB_PASSWORD} 從環境變數中讀取 `DB_PASSWORD` 的值。

### Log4j2 設定

- 排除 SpringBoot 預設的 Logback，改用 Log4j2。
- SpringBoot 使用 info；內部程式邏輯使用 debug。

### 連線驗證器 Validator

- 在 SpringBoot 啟動時，會自動驗證連線設定是否正確。
- 為保持開發彈性，不會因為 DB 連線失敗而啟動失敗，但會印出 Error Log。

### 核心 Metadata 載體物件 PostgreTableMeta

- 這是核心的 Metadata 載體物件，包含了 PostgreSQL 資料表的結構資訊。
- 所有的 Metadata 輸出都會基於這個物件進行處理。

### Markdown 產生器 MarkdownGenerator

- 負責將 PostgreTableMeta 轉換為 Markdown 格式。