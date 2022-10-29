import {Toast} from "vant";
import store from "@/store";

export default class Tools {

    //去掉特殊关键字
    static checkUpKeyWork(value: string): string {
        value = value.replace(".mp3","");
        value = value.replace(".flac","");
        value = value.replace(","," ");
        return value.replace("."," ");
    }

    //添加0
    static addZero (i: number): string {
        if (i <10) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    //检查文件类型
    static fileType (file: any): boolean {
        console.log(file.content.indexOf("audio/mpeg"));
        if (file.content.indexOf("audio/mpeg") === -1 && file.content.indexOf("audio/flac") === -1) {
            Toast.fail('文件格式不正确!');
            return false;
        }
        console.log("ok");
        return true;
    }
}