package com.lwkandroid.wingsdemo.bean;

import java.util.List;

/**
 * Created by LWK
 * TODO
 * 2017/12/7
 */

public class LoginHttpBean
{
    private BaseInfoBean base_info;
    private AnalyzeBean analyze;
    private StateBean state;
    private VrBean vr;
    private String uid;
    private String session_id;
    private double web_cache_version;
    private int is_open3D;
    private List<RoleBean> role;
    private List<GroupSetBean> group_set;

    public BaseInfoBean getBase_info()
    {
        return base_info;
    }

    public void setBase_info(BaseInfoBean base_info)
    {
        this.base_info = base_info;
    }

    public AnalyzeBean getAnalyze()
    {
        return analyze;
    }

    public void setAnalyze(AnalyzeBean analyze)
    {
        this.analyze = analyze;
    }

    public StateBean getState()
    {
        return state;
    }

    public void setState(StateBean state)
    {
        this.state = state;
    }

    public VrBean getVr()
    {
        return vr;
    }

    public void setVr(VrBean vr)
    {
        this.vr = vr;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getSession_id()
    {
        return session_id;
    }

    public void setSession_id(String session_id)
    {
        this.session_id = session_id;
    }

    public double getWeb_cache_version()
    {
        return web_cache_version;
    }

    public void setWeb_cache_version(double web_cache_version)
    {
        this.web_cache_version = web_cache_version;
    }

    public int getIs_open3D()
    {
        return is_open3D;
    }

    public void setIs_open3D(int is_open3D)
    {
        this.is_open3D = is_open3D;
    }

    public List<RoleBean> getRole()
    {
        return role;
    }

    public void setRole(List<RoleBean> role)
    {
        this.role = role;
    }

    public List<GroupSetBean> getGroup_set()
    {
        return group_set;
    }

    public void setGroup_set(List<GroupSetBean> group_set)
    {
        this.group_set = group_set;
    }

    public static class BaseInfoBean
    {
        private String uid;
        private String birthday;
        private String sex;
        private String intro;
        private String constellation;
        private String animal_sign;
        private String native_place;
        private String native_place_province;
        private String native_place_city;
        private String native_place_district;
        private String company;
        private String phone;
        private String name;
        private String head;
        private String vip_level;
        private String is_cxuser;
        private String default_img_id;
        private int ccbn_vip;
        private String hxpassword;
        private String is_super;

        public String getUid()
        {
            return uid;
        }

        public void setUid(String uid)
        {
            this.uid = uid;
        }

        public String getBirthday()
        {
            return birthday;
        }

        public void setBirthday(String birthday)
        {
            this.birthday = birthday;
        }

        public String getSex()
        {
            return sex;
        }

        public void setSex(String sex)
        {
            this.sex = sex;
        }

        public String getIntro()
        {
            return intro;
        }

        public void setIntro(String intro)
        {
            this.intro = intro;
        }

        public String getConstellation()
        {
            return constellation;
        }

        public void setConstellation(String constellation)
        {
            this.constellation = constellation;
        }

        public String getAnimal_sign()
        {
            return animal_sign;
        }

        public void setAnimal_sign(String animal_sign)
        {
            this.animal_sign = animal_sign;
        }

        public String getNative_place()
        {
            return native_place;
        }

        public void setNative_place(String native_place)
        {
            this.native_place = native_place;
        }

        public String getNative_place_province()
        {
            return native_place_province;
        }

        public void setNative_place_province(String native_place_province)
        {
            this.native_place_province = native_place_province;
        }

        public String getNative_place_city()
        {
            return native_place_city;
        }

        public void setNative_place_city(String native_place_city)
        {
            this.native_place_city = native_place_city;
        }

        public String getNative_place_district()
        {
            return native_place_district;
        }

        public void setNative_place_district(String native_place_district)
        {
            this.native_place_district = native_place_district;
        }

        public String getCompany()
        {
            return company;
        }

        public void setCompany(String company)
        {
            this.company = company;
        }

        public String getPhone()
        {
            return phone;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getHead()
        {
            return head;
        }

        public void setHead(String head)
        {
            this.head = head;
        }

        public String getVip_level()
        {
            return vip_level;
        }

        public void setVip_level(String vip_level)
        {
            this.vip_level = vip_level;
        }

        public String getIs_cxuser()
        {
            return is_cxuser;
        }

        public void setIs_cxuser(String is_cxuser)
        {
            this.is_cxuser = is_cxuser;
        }

        public String getDefault_img_id()
        {
            return default_img_id;
        }

        public void setDefault_img_id(String default_img_id)
        {
            this.default_img_id = default_img_id;
        }

        public int getCcbn_vip()
        {
            return ccbn_vip;
        }

        public void setCcbn_vip(int ccbn_vip)
        {
            this.ccbn_vip = ccbn_vip;
        }

        public String getHxpassword()
        {
            return hxpassword;
        }

        public void setHxpassword(String hxpassword)
        {
            this.hxpassword = hxpassword;
        }

        public String getIs_super()
        {
            return is_super;
        }

        public void setIs_super(String is_super)
        {
            this.is_super = is_super;
        }

        @Override
        public String toString()
        {
            return "BaseInfoBean{" +
                    "uid='" + uid + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", sex='" + sex + '\'' +
                    ", intro='" + intro + '\'' +
                    ", constellation='" + constellation + '\'' +
                    ", animal_sign='" + animal_sign + '\'' +
                    ", native_place='" + native_place + '\'' +
                    ", native_place_province='" + native_place_province + '\'' +
                    ", native_place_city='" + native_place_city + '\'' +
                    ", native_place_district='" + native_place_district + '\'' +
                    ", company='" + company + '\'' +
                    ", phone='" + phone + '\'' +
                    ", name='" + name + '\'' +
                    ", head='" + head + '\'' +
                    ", vip_level='" + vip_level + '\'' +
                    ", is_cxuser='" + is_cxuser + '\'' +
                    ", default_img_id='" + default_img_id + '\'' +
                    ", ccbn_vip=" + ccbn_vip +
                    ", hxpassword='" + hxpassword + '\'' +
                    ", is_super='" + is_super + '\'' +
                    '}';
        }
    }

    public static class AnalyzeBean
    {
        private String money;

        public String getMoney()
        {
            return money;
        }

        public void setMoney(String money)
        {
            this.money = money;
        }

        @Override
        public String toString()
        {
            return "AnalyzeBean{" +
                    "money='" + money + '\'' +
                    '}';
        }
    }

    public static class StateBean
    {
        private String is_travel;
        private String lon;
        private String lat;

        public String getIs_travel()
        {
            return is_travel;
        }

        public void setIs_travel(String is_travel)
        {
            this.is_travel = is_travel;
        }

        public String getLon()
        {
            return lon;
        }

        public void setLon(String lon)
        {
            this.lon = lon;
        }

        public String getLat()
        {
            return lat;
        }

        public void setLat(String lat)
        {
            this.lat = lat;
        }

        @Override
        public String toString()
        {
            return "StateBean{" +
                    "is_travel='" + is_travel + '\'' +
                    ", lon='" + lon + '\'' +
                    ", lat='" + lat + '\'' +
                    '}';
        }
    }

    public static class VrBean
    {
        private String buy;

        public String getBuy()
        {
            return buy;
        }

        public void setBuy(String buy)
        {
            this.buy = buy;
        }

        @Override
        public String toString()
        {
            return "VrBean{" +
                    "buy='" + buy + '\'' +
                    '}';
        }
    }

    public static class RoleBean
    {
        private String role_type;
        private String name;
        private String head;

        public String getRole_type()
        {
            return role_type;
        }

        public void setRole_type(String role_type)
        {
            this.role_type = role_type;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getHead()
        {
            return head;
        }

        public void setHead(String head)
        {
            this.head = head;
        }

        @Override
        public String toString()
        {
            return "RoleBean{" +
                    "role_type='" + role_type + '\'' +
                    ", name='" + name + '\'' +
                    ", head='" + head + '\'' +
                    '}';
        }
    }

    public static class GroupSetBean
    {
        private String group_id;
        private String type;

        public String getGroup_id()
        {
            return group_id;
        }

        public void setGroup_id(String group_id)
        {
            this.group_id = group_id;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        @Override
        public String toString()
        {
            return "GroupSetBean{" +
                    "group_id='" + group_id + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString()
    {
        return "LoginHttpBean{" +
                "base_info=" + base_info +
                ", analyze=" + analyze +
                ", state=" + state +
                ", vr=" + vr +
                ", uid='" + uid + '\'' +
                ", session_id='" + session_id + '\'' +
                ", web_cache_version=" + web_cache_version +
                ", is_open3D=" + is_open3D +
                ", role=" + role +
                ", group_set=" + group_set +
                '}';
    }
}
