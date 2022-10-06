//导入OSS
import Search from "@/Interface/Search";
import SoundQuality from "@/Interface/SoundQuality";
import {reqUpLoadSongList, reqUpLoadSongListDetailed} from "@/api";
import store from "@/store";
import {Notify, Toast} from "vant";

const OSS = require('ali-oss');
//实例化OSS
const client = new OSS({
    // yourRegion填写Bucket所在地域。以华东1（杭州）为例，yourRegion填写为oss-cn-hangzhou。
    region: 'XXX',
    // 从STS服务获取的临时访问密钥（AccessKey ID和AccessKey Secret）
    accessKeyId: 'XXX',
    accessKeySecret: 'XXX',
    // 填写Bucket名称。
    bucket: 'XXX'
});
//设置分片上传参数
const options = {
    // 获取分片上传进度、断点和返回值。
    progress: (p: number) => {
        // console.log("------p:",p);
        store.state.Schedule = Math.round((p * 100));
    },
    // 设置并发上传的分片数量。
    parallel: 1,
    // 设置分片大小。默认值为1 MB，最小值为100 KB。
    partSize: 1024 * 1024 * 1,
};

export async function uploadSongs(fileName: string, data: object, song_list: Search, song_list_detailed: SoundQuality) {
    try {
        let result = await client.multipartUpload(fileName, data, {...options});          //AJAX
        let url = result.res.requestUrls[0];
        let index = url.indexOf('?uploadId=');
        url = url.slice(0, index);
        try {
            //上传song_list
            let id = (await reqUpLoadSongList(song_list))[0].id;    //AJAX
            //上传song_list_detailed
            song_list_detailed.url = url;
            song_list_detailed.uid= id;
            await reqUpLoadSongListDetailed(song_list_detailed);    //AJAX
            Toast.success("成功上传!");
        } catch (e) {
            //加载层
            store.state.isShowSchedule = false;
            store.state.Schedule = 0;
            console.log(e);
            Toast.fail('有重复歌名');
            return;
        }
    } catch (e) {
        console.log(e);
        Notify({ type: 'danger', message: '网络错误' });
    }
    //加载层
    store.state.isShowSchedule = false;
    store.state.Schedule = 0;
}
