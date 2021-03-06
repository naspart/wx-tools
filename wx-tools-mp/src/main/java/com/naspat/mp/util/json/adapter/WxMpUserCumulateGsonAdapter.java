package com.naspat.mp.util.json.adapter;

import com.google.gson.*;
import com.naspat.common.util.json.GsonHelper;
import com.naspat.mp.bean.data_cube.WxMpDataCubeUserCumulate;
import org.apache.commons.lang3.time.FastDateFormat;

import java.lang.reflect.Type;
import java.text.ParseException;

public class WxMpUserCumulateGsonAdapter implements JsonDeserializer<WxMpDataCubeUserCumulate> {

    private static final FastDateFormat DATE_FORMAT = FastDateFormat
            .getInstance("yyyy-MM-dd");

    @Override
    public WxMpDataCubeUserCumulate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WxMpDataCubeUserCumulate cumulate = new WxMpDataCubeUserCumulate();
        JsonObject summaryJsonObject = json.getAsJsonObject();

        try {
            String refDate = GsonHelper.getString(summaryJsonObject, "ref_date");
            if (refDate != null) {
                cumulate.setRefDate(DATE_FORMAT.parse(refDate));
            }
            cumulate.setCumulateUser(GsonHelper.getInteger(summaryJsonObject, "cumulate_user"));
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
        return cumulate;

    }
}
