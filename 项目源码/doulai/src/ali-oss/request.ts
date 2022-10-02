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

export async function uploadSongs(fileName: string, data: object, song_list: Search, song_list_detailed: SoundQuality) {
    try {
        let result = await client.put(fileName, data);          //AJAX
        let url = result.url;
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
            store.state.isShowOverlay = false;
            console.log(e);
            Toast.fail('有重复歌名');
            return;
        }
    } catch (e) {
        console.log(e);
        Notify({ type: 'danger', message: '网络错误' });
    }
    //加载层
    store.state.isShowOverlay = false;
}
