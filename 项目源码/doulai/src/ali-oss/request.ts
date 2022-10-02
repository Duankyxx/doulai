//导入OSS
import Search from "@/Interface/Search";
import SoundQuality from "@/Interface/SoundQuality";
import {reqUpLoadSongList, reqUpLoadSongListDetailed} from "@/api";
import store from "@/store";
import {Toast} from "vant";

const OSS = require('ali-oss');
//实例化OSS
const client = new OSS({
    // yourRegion填写Bucket所在地域。以华东1（杭州）为例，yourRegion填写为oss-cn-hangzhou。
    region: 'xxx',
    // 从STS服务获取的临时访问密钥（AccessKey ID和AccessKey Secret）。
    accessKeyId: 'xxx',
    accessKeySecret: 'xxx',
    // 填写Bucket名称。
    bucket: 'xxx'
});

export async function uploadSongs(fileName: string, data: object, song_list: Search, song_list_detailed: SoundQuality) {
    let result = await client.put(fileName, data);
    let url = result.url;
    //上传song_list
    let id = (await reqUpLoadSongList(song_list))[0].id;
    //上传song_list_detailed
    song_list_detailed.url = url;
    song_list_detailed.uid= id;
    await reqUpLoadSongListDetailed(song_list_detailed);
    //加载层
    store.state.isShowOverlay = false;
    Toast.success("成功上传!");
}
