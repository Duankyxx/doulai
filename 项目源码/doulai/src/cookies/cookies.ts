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
        localStorage.setItem("username", username);
        localStorage.setItem("password", password);
        return resolve;
    }).then(() => {
        console.log("账号密码保存成功!");
    });
}
//读取
export const getAccount = (): Promise<any> => {
    return new Promise((resolve, reject) => {
        const username: string|null = localStorage.getItem("username");
        const password: string|null = localStorage.getItem("password");
        if (username === null || password === null) return reject;
        return resolve({
            "username": username,
            "password": password
        });
    });
}
//异步登录
export const loginAccount = async (): Promise<void> => {
    let res: User = await getAccount();
    let rs = await reqLogin({
        id: 0,
        username: res.username,
        password: res.password,
        sex: 0,
    });
    if (rs === "") {
        Toast.fail('请重新登录!');
        return;
    }
    //登录成功
    store.state.isLogin = true;
    store.state.User = {...rs};
}
//退出登录
export const signOut = (): void => {
    Dialog.confirm({
        title: "确认退出账号吗?",
        message: "下次需要重新登录!",
    }).then(() => {
        localStorage.removeItem("username");
        localStorage.removeItem("password");
        store.state.isLogin = false;
        store.state.User = undefined;
    });
}