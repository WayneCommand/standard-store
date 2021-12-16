package ltd.inmind.delivery.records;

public record Package(String ticketId,String senderName,String receiverName, Long status) {

    public static final Long CREATED = 1L;
    public static final Long PROCESSING = 2L;
    public static final Long DONE = 3L;
    public static final Long CANCEL = 4L;

    public static Package create(String senderName, String receiverName) {
        String id = String.valueOf(System.nanoTime());
        return new Package(id, senderName, receiverName, CREATED);
    }

    public Package sign() {
        return new Package(this.ticketId, this.senderName, this.receiverName, DONE);
    }
}
