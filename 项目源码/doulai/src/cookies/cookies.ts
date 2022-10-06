import {reqLogin} from "@/api";
import store from "@/store";
import {Dialog, Toast} from "vant";
import 'vant/es/dialog/style';

const Cookies = require('js-cookie');

interface User {
    username: string;
    password: string;
}

//写入
export const setAccount = (username: string, password: string): void => {
    new Promise((resolve, reject) => {
        Cookies.set("username", username);
        Cookies.set("password", password);
        return resolve;
    }).then(() => {
        console.log("账号密码保存成功!");
    });
}
//读取
export const getAccount = (): Promise<any> => {
    return new Promise((resolve, reject) => {
        const username: string|undefined = Cookies.get("username");
        const password: string|undefined = Cookies.get("password");
        if (username === undefined || password === undefined) return reject;
        return resolve({
            "username": username,
            "password": password
        });
    });
}
//异步登录
export const loginAccount = (): void => {
    getAccount().then((res: User) => {
        reqLogin({
            id: 0,
            username: res.username,
            password: res.password,
            sex: 0,
        }).then((rs) => {
            if (rs === "") {
                Toast.fail('请重新登录!');
                return;
            }
            //登录成功
            store.state.isLogin = true;
            store.state.User = {...rs};
        });
    });
}
//退出登录
export const signOut = (): void => {
    Dialog.confirm({
        title: "确认退出账号吗?",
        message: "下次需要重新登录!",
    }).then(() => {
        Cookies.remove('username');
        Cookies.remove('password');
        store.state.isLogin = false;
        store.state.User = undefined;
    });
}