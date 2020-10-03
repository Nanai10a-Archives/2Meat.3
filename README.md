# (Kotlin)2Meat

### 概要

Nanai10aのDiscordbot

### 経歴

ゴミカス

### 今回

適当

## Architecture

**屑**

### 詳細

coreのjarにはJDAインスタンスがあるだけにします
後は依存関係として別途Listenerなクラスを作成してください

jarのrootに`listener.2tjson`という指定されたJsonフォーマットのファイルを同梱させます
それをcoreが読み込んでlistenerとして登録をします
もしも読み込めない場合は例外を吐いて落ちます

## hosting-commands

## Annotation

\* - 権限が必要

### dialoger - 2dl>

*if-script*に*this*を記述することで*then-script*から*that*をぶん投げる害悪機能。
独自の*script*解析が必要。

*this*に使えるのは
・Discord-events
・if
・DateTime

#### commands

\*`add` - *script*の登録を行う
`check` - *script*のparseを行い結果を出力する
`list` - 登録済み*script*一覧を表示する
`show` - 指定*script*の内容を表示する
`search` - 条件に合う*script*を表示する
`hist`(history) - 指定*script*のlogを表示する
\*`del`(delete) - 登録済み*script*を削除する

`help` - help me!
`info` - *misc*
`@mention` - 概要

### isso-command - isso>

簡潔に言えば某`meigen`と同じシステム
がここでは`tag`を実装する

某氏の *† careless mistake †* を記録するために作ったもの
ミスの*genre*を`tag`として実装する

#### commands



\*`add` - 登録する
`list` - 一覧を表示する
`show` - 指定したものを表示する
`search` - 条件に合うものを表示する
\*`del`(delete) - 削除する

`help` - help me!
`info` - *misc*
`@mention` - 概要

### toast - ts>

#### commands

//考案中

`help` - help me!
`info` - *misc*
`@mention` - 概要

