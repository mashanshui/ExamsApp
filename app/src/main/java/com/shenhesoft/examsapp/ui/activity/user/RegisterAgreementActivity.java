package com.shenhesoft.examsapp.ui.activity.user;

import android.os.Bundle;
import android.widget.TextView;

import com.shenhesoft.examsapp.R;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/6/29
 * @desc TODO
 */
public class RegisterAgreementActivity extends XTitleActivity {

    @BindView(R.id.agreement_1)
    TextView agreement1;
    @BindView(R.id.agreement_2)
    TextView agreement2;
    @BindView(R.id.agreement_3)
    TextView agreement3;
    @BindView(R.id.agreement_4)
    TextView agreement4;
    @BindView(R.id.agreement_5)
    TextView agreement5;
    @BindView(R.id.agreement_6)
    TextView agreement6;
    @BindView(R.id.agreement_7)
    TextView agreement7;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("注册协议");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        agreement1.setText("青阳MPAcc(以下亦称“本网站”)同意按照本协议的规定及其不定时发布的操作规则提供基于互联网和移动互联网的相关服务(以下称“网络服务”)。\n" +
                "为获得网络服务, 申请人应当认真阅读、充分理解本《协议》中各条款, 包括免除或者限制青阳MPAcc责任的免责条款及对用户的权利限制条款。审慎阅读并选择接受或不接受本《协议》(未成年人应在法定监护人陪同下阅读)。\n" +
                "同意接受本协议的全部条款的, 申请人应当按照页面上的提示完成全部的注册程序, 并在注册程序过程中点击“同意”按钮, 否则视为不接受本《协议》全部条款, 申请人应当终止并退出申请。\n" +
                "本《协议》可由青阳MPAcc定期更新, 更新后的协议条款一旦公布即代替原来的协议条款, 恕不再另行通知, 用户可在青阳MPAcc查阅最新版协议条款。在青阳MPAcc修改《协议》条款后, 如果用户不接受修改后的条款, 请立即停止使用青阳MPAcc提供的网络服务, 继续使用的用户将被视为已接受了修改后的协议。");
        agreement2.setText("1、网络服务的具体内容由青阳MPAcc根据实际情况提供, 包括学习资讯、学习工具、在线题库相关应用等服务。\n" +
                "2、青阳MPAcc提供的网络服务包括收费和免费。收费服务包括但不限于青阳MPAcc的部分收费课程, 用户使用收费网络服务需要支付约定费用。对于收费服务, 青阳MPAcc会在用户使用之前给予用户明确的提示, 只有用户根据提示确认同意支付相关费用, 用户才能使用该等收费服务。如用户未支付相关费用, 则青阳MPAcc有权不向用户提供该等收费服务。\n" +
                "3、用户理解, 青阳MPAcc仅提供青阳MPAcc明确承诺的网络服务, 除此之外与相关网络服务有关的设备(如个人电脑、手机、及其他与接入互联网或移动网有关的装置)及所需的费用(如为接入互联网而支付的电话费及上网费、为使用移动网络而支付的手机费)均应由用户自行负担。\n" +
                "4、除特别说明外，青阳MPAcc包括但不限于PC端和移动端下载课件在内的所有服务均附期限，到期终止。用户应在截止日期前享受其购买的服务。");
        agreement3.setText("1、经青阳MPAcc系统完成注册程序并通过身份认证的用户即为正式用户。\n" +
                "2、如发现用户帐号中含有不雅文字或不恰当名称的, 青阳MPAcc保留注销其用户帐号的权利。\n" +
                "3、用户帐号的所有权归青阳MPAcc, 用户完成申请注册手续后, 用户享有使用权。3个月未使用的用户账号, 青阳MPAcc保留收回的权利。\n" +
                " 4、用户有义务保证密码和帐号的安全, 用户利用该帐号所进行的一切活动引起的任何损失或损害, 由用户自行承担全部责任, 青阳MPAcc不承担任何责任。如用户发现帐号遭到未授权的使用或发生其他任何安全问题, 应立即修改账号密码并妥善保管。因黑客行为或用户的保管疏忽导致帐号非法使用, 青阳MPAcc不承担任何责任。\n" +
                "5、用户帐号和密码仅由初始申请注册人使用，用户不得将自己用户账户或密码有偿或无偿以转让、出借、授权等方式提供给第三人操作和使用, 否则用户应当自行承担因违反此要求而遭致的任何损失。违反本项约定的，青阳MPAcc保留收回账号的权利。若因违反本约定对他人造成损害的，用户应与实际使用人承担连带赔偿责任，同时青阳MPAcc保留追究用户违约责任的权利。\n" +
                "6、用户帐号在丢失、遗忘密码及因合用产生使用权归属纠纷后, 须遵照青阳MPAcc的申诉途径请求找回帐号。用户可以凭初始注册资料向青阳MPAcc申请找回帐号。青阳MPAcc的账户恢复机制仅负责识别申请用户所提资料与系统记录资料是否一致, 而无法识别申诉人是否系帐号的真正使用权人。对用户因被他人冒名申请而致的任何损失, 青阳MPAcc不承担任何责任, 用户知晓帐号及密码保管责任在于用户, 青阳MPAcc并不承诺帐号丢失或遗忘密码后用户一定能通过申诉找回帐号。用户应当谨慎填写初始注册邮箱作为确认接收争议帐号的指定邮箱。\n" +
                "7、青阳MPAcc建议用户应当使用本人名义为用户账户充值或行使付款行为。若用户存在使用第三人的名义进行充值或付款，或委托第三人代为充值或付款之行为的，则以上行为被视作本人的行为，若由于该第三人行为导致充值或付款行为失败或成功后又被撤销的，均被认为是用户本人真实意思的表示和用户本人实施的行为，由此所造成的一切法律后果均由用户自行承担。");
        agreement4.setText("1、用户在使用青阳MPAcc网络服务过程中, 必须遵循国家的相关法律法规, 不得发布危害国家安全、色情、暴力、侵犯版权等任何合法权利等非法内容; 也不得利用青阳MPAcc平台发布含有虚假、有害、胁迫、侵害他人隐私、骚扰、侵害、中伤、粗俗、或其它道德上令人反感的内容。\n" +
                "2、青阳MPAcc可依其合理判断, 对违反有关法律法规或本协议约定; 或侵犯、妨害、威胁任何人权利或安全的内容, 或者假冒他人的行为, 青阳MPAcc有权停止传输任何前述内容, 并有权依其自行判断对违反本条款的任何用户采取适当的法律行动, 包括但不限于, 删除具有违法性、侵权性、不当性等内容, 阻止其使用青阳MPAcc全部或部分网络服务, 并且依据法律法规保存有关信息并向有关部门报告等。\n" +
                "3、用户通过青阳MPAcc网络服务所发布的任何内容并不反映沪江的观点或政策, 沪江对此不承担任何责任。用户须对上述内容的真实性、合法性、无害性、有效性等全权负责, 与用户所发布信息相关的任何法律责任由用户自行承担, 与青阳MPAcc无关。");
        agreement5.setText("青阳MPAcc提供的网络服务中包含的任何文本、图片、图形、音频和/或视频资料均受版权、商标和/或其它财产所有权法律的保护, 未经相关权利人同意, 上述资料均不得在任何媒体直接或间接发布、播放、出于播放或发布目的而改写或再发行, 或者被用于其他任何商业目的。所有以上资料或资料的任何部分仅可作为私人和非商业用途保存。青阳MPAcc不就由上述资料产生或在传送或递交全部或部分上述资料过程中产生的延误、不准确、错误和遗漏或从中产生或由此产生的任何损害赔偿, 以任何形式, 向用户或任何第三方负责。");
        agreement6.setText("1、保护用户隐私是青阳MPAcc的一项基本政策, 青阳MPAcc保证不对外公开或向第三方提供用户的注册资料及用户在使用网络服务时存储在青阳MPAcc内的非公开内容, 但下列情况除外:\n" +
                "（1）事先获得用户的书面明确授权;\n" +
                "（2）根据有关的法律法规要求;\n" +
                "（3）按照相关政府主管部门的要求;\n" +
                "（4）为维护社会公众的利益;\n" +
                "（5）为维护青阳MPAcc的合法权益;\n" +
                "2、为了更好地为用户提供全面服务，用户同意青阳MPAcc可将用户注册资料及使用信息提供沪江关联公司使用。青阳MPAcc保证前述关联公司同等级地严格遵循本协议第六条第1款之隐私保护责任。\n" +
                "3、用户同意：青阳MPAcc或青阳MPAcc运营商的关联公司在必要时有权根据用户注册时或接受服务时所提供的联系信息（包括但不限于电子邮件地址、联系电话、联系地址、即时聊天工具账号等），通过电子邮件、电话、短信、邮寄、即时聊天、弹出页面等方式向用户发送如下信息：\n" +
                "（1）各类重要通知信息，可能包括但不限于订单、交易单、修改密码提示等重要信息。此类信息可能对用户的权利义务产生重大的有利或不利影响，用户务必及时关注。\n" +
                "（2）商品和服务广告、促销优惠等商业性信息。若用户不愿意接收此类信息，则可通过青阳MPAcc或青阳MPAcc运营商关联公司所提供的相应退订功能（若有）进行退订。");
        agreement7.setText("1、本协议之服务条款用以规范用户使用青阳MPAcc提供的服务。\n" +
                "2、鉴于国家法律法规不时变化及青阳MPAcc运营之需要，青阳MPAcc有权对本协议条款不时地进行修改，修改后的协议一旦被公布于青阳MPAcc上即告生效，并替代原来的协议。 用户有义务不时关注并阅读最新版的协议及网站公告。如用户不同意更新后的协议，则应立即停止接受青阳MPAcc依据本协议提供的服务；若用户继续使用青阳MPAcc提供的服务的，即视为同意更新后的协议。 如果本协议中任何一条被视为废止、无效或因任何理由不可执行，该条应视为可分的且并不影响任何其余条款的有效性和可执行性。");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_agreement;
    }

    @Override
    public Object newP() {
        return null;
    }
}
