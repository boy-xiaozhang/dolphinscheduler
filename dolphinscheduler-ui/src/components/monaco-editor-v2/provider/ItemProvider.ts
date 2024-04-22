/**
 * @Author Jegger
 * @Date 2024/4/21 11:28
 * @Version 1.0
 * @Description
 **/
import * as monaco from 'monaco-editor';
import {editor, Position} from "monaco-editor";
import {language as sqlLanguage} from "monaco-editor/esm/vs/basic-languages/sql/sql"

// 模拟数据库表
const tables = {
    'bi_ods': ['aaa', 'bbb', 'ccc'],
    'bi_dwd': ['xxx', 'yyy', 'zzz']
};

export default {
    sql: {
        provide: function provideCompletionItems(model: editor.ITextModel, position: Position) {
            let suggestions: any[] = []

            const {lineNumber, column} = position
            const textBeforePointer = model.getValueInRange({
                startLineNumber: lineNumber,
                startColumn: 0,
                endLineNumber: lineNumber,
                endColumn: column,
            })

            const contents = textBeforePointer.trim().split(/\s+/)

            const lastContents = contents[contents?.length - 1] // 获取最后一段非空字符串

            if (lastContents) {
                const match = lastContents.match(/(\w+)\./);
                if (match) {
                    const dbName = match[1];
                    if (tables[dbName]) {
                        tables[dbName].forEach(table => {
                            suggestions.push(
                                {
                                    label: table, // 显示的提示内容;默认情况下，这也是选择完成时插入的文本。
                                    insertText: table, // 选择此完成时应插入到文档中的字符串或片段
                                    kind: monaco.languages.CompletionItemKind.Field, // 此完成项的种类。编辑器根据图标的种类选择图标。
                                }
                            )
                        })
                    }
                } else {
                    const sqlConfigKey = ['builtinFunctions', 'keywords', 'operators']
                    sqlConfigKey.forEach(key => {
                        sqlLanguage[key].forEach(sql => {
                            suggestions.push(
                                {
                                    label: sql, // 显示的提示内容;默认情况下，这也是选择完成时插入的文本。
                                    insertText: sql, // 选择此完成时应插入到文档中的字符串或片段
                                    kind: monaco.languages.CompletionItemKind['Function'], // 此完成项的种类。编辑器根据图标的种类选择图标。
                                }
                            )
                        })

                    })
                }
            }


            return {
                suggestions
            };
        }
    }
}