package com.ovoenergy.kafka.serialization.json4s

import java.nio.charset.StandardCharsets

import com.ovoenergy.kafka.serialization.core.Format
import com.ovoenergy.kafka.serialization.testkit.UnitSpec
import org.json4s.DefaultFormats
import org.json4s.native.{Serialization => JsonSerialization}

class Json4sSerializationSpec extends UnitSpec with Json4sSerialization{

  import StandardCharsets.UTF_8

  import JsonSerialization._
  import com.ovoenergy.kafka.serialization.testkit.UnitSpec._

  implicit val formats = DefaultFormats

  "Json4sSerialization" when {
    "serializing" should {
      "write the Json json body" in forAll { event: Event =>

        val serializer = json4sSerializer[Event]

        val bytes = serializer.serialize("Does not matter", event)

        read[Event](new String(bytes, UTF_8)) shouldBe event
      }
    }

    "deserializing" should {
      "parse the json" in forAll { event: Event =>

        val deserializer = json4sDeserializer[Event]

        val bytes = write(event).getBytes(UTF_8)

        deserializer.deserialize("Does not matter", bytes) shouldBe event
      }
    }
  }

}
