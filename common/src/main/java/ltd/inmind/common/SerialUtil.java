package ltd.inmind.common;

import java.io.*;
import java.util.Objects;

/**
 * 序列化工具（原生）
 * @since 1.0
 */
public class SerialUtil {

    /**
     * 序列化
     * @param obj               对象
     * @return                  封装好的可传输的值
     * @throws IOException      序列化异常
     */
    public static byte[] toBytes(Serializable obj) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutput objectOutput = new ObjectOutputStream(os);
        objectOutput.writeObject(obj);
        objectOutput.flush();
        return os.toByteArray();
    }

    /**
     * 反序列化
     * @param bytes                     封装好的传输的值
     * @return                          对象
     * @throws IOException              序列化异常
     * @throws ClassNotFoundException   类型未找到异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        ObjectInput objectInput = new ObjectInputStream(is);

        final Object obj = objectInput.readObject();
        if (Objects.isNull(obj))
            throw new InvalidObjectException("object is null.");

        return (T) obj;
    }

}
