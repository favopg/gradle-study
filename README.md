# gradle-study
javaコードを書いていくプロジェクト

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
`git add ファイル名 or git add -A`

* ブランチにプッシュする
`git push origin ブランチ名`