# gradle-study
javaコードを書いていくプロジェクト
勉強意識としてはテストコードを重点に置く

## Java13、Junit5を採用

## よく使うgradleコマンド集
* コンパイル
`gradle build`

* 実行
`gradle run`

* テストクラス実行
`gradle test`

* 特定のテストクラス実行
`gradle test --tests "*特定のクラス名" -i`

* 特定のメソッドのみテスト
`gradle test --tests "*特定のクラス名.メソッド名" -i`


## よく使うgitコマンド集
* 前回コミットからの差分確認
`git diff HEAD`

* 最新ソース取得
`git pull origin ブランチ名`

* ステータス確認（変更状況を確認できる）
`git status`

* ファイルを退避する
`git stash save`

* ステージング環境に追加する
`git add ファイル名  or git add -A`

* ブランチにプッシュする
`git push origin ブランチ名`

* 新規ブランチを作成し、チェックアウトする
`git branch -b ブランチ名`


## Junitの便利アノテーション
* パラメータテスト時に使用する 暗黙的な型変換までやってくれる
`@ParameterizedTest @ValueSource(小文字データ型 + s = 配列)`