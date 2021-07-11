package com.example.mobileprogrammingproject.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.mobileprogrammingproject.R
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobileprogrammingproject.MainMenuActivity
import com.example.mobileprogrammingproject.databinding.FragmentPlayBinding
import java.util.*

class PlayFragment : Fragment() {

    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!
    private val diceImages = mutableListOf<Int>()
    private lateinit var animation1: Animation
    private lateinit var animation2: Animation
    private lateinit var animation3: Animation
    private lateinit var animation4: Animation
    private val args:PlayFragmentArgs by navArgs()


    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var isPlayButtonActivated = (activity as MainMenuActivity).isPlayButtonActivated
        var isAcceptButtonActivated = (activity as MainMenuActivity).isAcceptButtonActivated
        var isScoreButtonActivated = (activity as MainMenuActivity).isScoreButtonActivated
        var playButtonColor = (activity as MainMenuActivity).playButtonColor
        var acceptButtonColor = (activity as MainMenuActivity).acceptButtonColor
        var scoreButtonColor = (activity as MainMenuActivity).scoreButtonColor
        var combo = (activity as MainMenuActivity).combo
        var ViewModelArrayDices = (activity as MainMenuActivity).arrayDices


        val username = (activity as MainMenuActivity).userName
        //vibration effect
        val vibe = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val vibeEff = VibrationEffect.createOneShot(50, 100)

        //values that will be passed during play
        var numberRoll : Int = args.rollsNumberArg
        var Score: Int = args.totalScore
        var DoubleBool = args.doubleBool
        var TrisBool = args.trisBool
        var quaterBool = args.quaterBool
        var yahtzeeBool = args.yahtzeeBool
        var FourASCBool = args.fourASCBool
        var FiveASCBool = args.fiveASCBool
        var chanceBool = args.chanceBool
        var BonusBool = args.bonusBool
        var FullBool = args.fullBool
        var lastCombo : String = args.lastCombo1
        var x = args.noRoll
        var PartialScore = (activity as MainMenuActivity).playerscore


        //where our scoring array will be saved
        val rollResults = mutableListOf<Int>()


        //start binding section
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        val view = binding.root
        //end binding section

        binding.Score.text = "${PartialScore} "+ getString(R.string.Points)

        //some init
        binding.username.text = username
        binding.numberRolls.text = resources.getString(R.string.rolls_remaining)+" ${13-numberRoll}"
        //end init

        //initializing buttons
        binding.RollerAndChecker.isEnabled = isPlayButtonActivated
        binding.RollerAndChecker.setBackgroundColor(resources.getColor(playButtonColor))
        binding.seeResults.isEnabled = isScoreButtonActivated
        binding.seeResults.setBackgroundColor(resources.getColor(scoreButtonColor))
        binding.AcceptBtn.isEnabled = isAcceptButtonActivated //(false the first time)
        binding.AcceptBtn.setBackgroundColor(resources.getColor(acceptButtonColor)) //(grigio the first time)
        if((combo == "nessuna combo") or (combo == "") or (combo == "no combo")){
            binding.ComboReader.text = combo}
            else{
                binding.ComboReader.text = combo.substring(0 until (combo.lastIndex))}



        binding.AcceptBtn.setOnClickListener {
            binding.ComboReader.text = ""
            (activity as MainMenuActivity).editCombo("")
            binding.Score.text = ""
            binding.dicesRow.visibility = View.INVISIBLE

            binding.seeResults.isEnabled = true
            binding.seeResults.setBackgroundColor(resources.getColor(R.color.giallino))
            (activity as MainMenuActivity).editScoreButton()
            (activity as MainMenuActivity).editScoreButtonColor()

            binding.RollerAndChecker.isEnabled = true
            (activity as MainMenuActivity).editPlayButton()
            binding.RollerAndChecker.setBackgroundColor(resources.getColor(R.color.giallino))
            (activity as MainMenuActivity).editPlayButtonColor()

            binding.AcceptBtn.isEnabled = false
            (activity as MainMenuActivity).editAcceptButton()
            binding.AcceptBtn.setBackgroundColor(resources.getColor(R.color.grey))
            (activity as MainMenuActivity).editAcceptButtonColor()

            if((combo==resources.getString(R.string.coppia) && DoubleBool==false) or (combo==resources.getString(R.string.tris) && TrisBool == false) or (combo==resources.getString(R.string.quater) && quaterBool == false) or (combo==resources.getString(R.string.full) && FullBool == false) or (combo==resources.getString(R.string.scala_da_4) && FourASCBool == false) or (combo==resources.getString(R.string.scala_da_5) && FiveASCBool == false)){
                Score += PartialScore
            }
            if(((combo==resources.getString(R.string.coppia) && DoubleBool==true)or(combo==resources.getString(R.string.tris) && TrisBool == true)or(combo==resources.getString(R.string.quater) && quaterBool == true)or(combo==resources.getString(R.string.full) && FullBool == true)or(combo==resources.getString(R.string.scala_da_4) && FourASCBool == true)or(combo==resources.getString(R.string.scala_da_5) && FiveASCBool == true))and !chanceBool){
                chanceBool=true
                Score+=25
            }
            if(combo==resources.getString(R.string.coppia)){
                DoubleBool = true
            }
            if(combo==resources.getString(R.string.tris)){
                TrisBool = true
            }
            if(combo==resources.getString(R.string.quater)){
                quaterBool = true
            }
            if(combo==resources.getString(R.string.yahtzee)){
                yahtzeeBool = true
            }
            if((combo==resources.getString(R.string.yahtzee))and yahtzeeBool){
                Score+=50
            }
            if(combo==resources.getString(R.string.full)){
                FullBool=true
            }
            if(combo==resources.getString(R.string.scala_da_4)){
                FourASCBool=true
            }
            if(combo==resources.getString(R.string.scala_da_5)){
                FiveASCBool=true
            }
            if((Score>100) and !BonusBool){
                BonusBool=true
                Score+=35
            }


            if(numberRoll == 13){
                binding.seeResults.isEnabled = false
                (activity as MainMenuActivity).editScoreButton()
                binding.seeResults.setBackgroundColor(resources.getColor(R.color.grey))
                (activity as MainMenuActivity).editScoreButtonColor()
            }
        }
        //end accept

        //start see result button
        binding.seeResults.setOnClickListener {
            if(!x){
                lastCombo = combo
                val action = PlayFragmentDirections.actionPlayFragmentToResultsFragment(currentRollsNumberArg = numberRoll,totalScore = Score,doubleBool = DoubleBool, trisBool = TrisBool,quaterBool = quaterBool,yahtzeeBool = yahtzeeBool,fourASCBool = FourASCBool,fiveASCBool = FiveASCBool,fullBool = FullBool,chanceBool = chanceBool,bonusBool = BonusBool,lastCombo2 = lastCombo,noRoll = x)
                findNavController().navigate(action)
            }else{
                binding.numberRolls.startAnimation(animation4)
            }
        }
        //end see result button

        //start roll section
        val arrayDices = mutableListOf(binding.FirstRoll, binding.SecondRoll, binding.ThirdRoll, binding.FourthRoll, binding.FifthRoll)

        diceImagesAdder(diceImages)

        animation1 = AnimationUtils.loadAnimation(this.context, R.anim.shake_animation)
        animation2 = AnimationUtils.loadAnimation(this.context, R.anim.fade_in_delay)
        animation3 = AnimationUtils.loadAnimation(this.context, R.anim.fade_in)
        animation4 = AnimationUtils.loadAnimation(this.context, R.anim.norollanimation)

        //setting up listeners
        binding.RollerAndChecker.setOnClickListener {

            binding.seeResults.isEnabled = false
            binding.seeResults.setBackgroundColor(resources.getColor(R.color.grey))
            (activity as MainMenuActivity).editScoreButton()
            (activity as MainMenuActivity).editScoreButtonColor()

            binding.RollerAndChecker.isEnabled = false
            binding.RollerAndChecker.setBackgroundColor(resources.getColor(R.color.grey))
            (activity as MainMenuActivity).editPlayButton()
            (activity as MainMenuActivity).editPlayButtonColor()

            binding.AcceptBtn.isEnabled = true
            binding.AcceptBtn.setBackgroundColor(resources.getColor(R.color.giallino))
            (activity as MainMenuActivity).editAcceptButton()
            (activity as MainMenuActivity).editAcceptButtonColor()

            x = false
            if (numberRoll<13) {
                numberRoll++
                vibe.vibrate(vibeEff)
                rollResults.removeAll(listOf(1, 2, 3, 4, 5, 6))
                binding.dicesRow.visibility = View.VISIBLE
                for (i in 0..4) {
                    getRandomValue(arrayDices[i], rollResults)
                }
                binding.dicesRow.startAnimation(animation3)
                //binding.numberRolls.startAnimation(animation2)
                binding.ComboReader.startAnimation(animation2)
                binding.Score.startAnimation(animation2)
                binding.numberRolls.text = resources.getString(R.string.rolls_remaining)+" ${13-numberRoll}"
                if ((getCombo(rollResults) == "nessuna combo") or (getCombo(rollResults) == "no combo")){binding.ComboReader.text = getCombo(rollResults)}
                else{binding.ComboReader.text = getCombo(rollResults) .substring(0..(getCombo(rollResults).lastIndex)-1)} //[0..getCombo(rollResults).lastIndex]
                PartialScore=getScore(getCombo(rollResults))
                (activity as MainMenuActivity).editScore(getScore(getCombo(rollResults)))
                binding.Score.text = "${PartialScore} "+ getString(R.string.Points)
                combo = getCombo(rollResults)
                (activity as MainMenuActivity).editCombo(getCombo(rollResults))


                //user should want to change some dice results
                binding.FirstRoll.isClickable = true
                binding.SecondRoll.isClickable = true
                binding.ThirdRoll.isClickable = true
                binding.FourthRoll.isClickable = true
                binding.FifthRoll.isClickable = true


                if (numberRoll == 13) {
                    binding.RollerAndChecker.text = getString(R.string.End_game)
                    binding.seeResults.isClickable= false
                }
            } else {
                val action = PlayFragmentDirections.actionPlayFragmentToEndGameFragment(myScoreArg2 = Score,username = username)
                findNavController().navigate(action)
            }


        }
        binding.FirstRoll.setOnClickListener {
            val random = Random().nextInt(6)

            arrayDices[0].setImageResource(diceImages.elementAt(random))
            arrayDices[0].startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.shake_animation))
            rollResults.removeAt(0)
            rollResults.add(0,random+1)
            if (getCombo(rollResults) == "nessuna combo"){binding.ComboReader.text = getCombo(rollResults)}
            else{binding.ComboReader.text = getCombo(rollResults) .substring(0..(getCombo(rollResults).lastIndex)-1)} //[0..getCombo(rollResults).lastIndex]
            PartialScore=getScore(getCombo(rollResults))
            (activity as MainMenuActivity).editScore(PartialScore)
            binding.Score.text = "${PartialScore} "+ getString(R.string.Points)
            binding.FirstRoll.isClickable = false
            combo = getCombo(rollResults)
        }
        binding.SecondRoll.setOnClickListener {
            val random = Random().nextInt(6)

            arrayDices[1].setImageResource(diceImages.elementAt(random))
            arrayDices[1].startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.shake_animation))
            rollResults.removeAt(1)
            rollResults.add(1,random+1)
            if (getCombo(rollResults) == "nessuna combo"){binding.ComboReader.text = getCombo(rollResults)}
            else{binding.ComboReader.text = getCombo(rollResults) .substring(0..(getCombo(rollResults).lastIndex)-1)} //[0..getCombo(rollResults).lastIndex]
            PartialScore=getScore(getCombo(rollResults))
            (activity as MainMenuActivity).editScore(PartialScore)
            binding.Score.text = "${PartialScore} "+ getString(R.string.Points)
            binding.SecondRoll.isClickable = false
            combo = getCombo(rollResults)
        }
        binding.ThirdRoll.setOnClickListener {
            val random = Random().nextInt(6)

            arrayDices[2].setImageResource(diceImages.elementAt(random))
            arrayDices[2].startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.shake_animation))
            rollResults.removeAt(2)
            rollResults.add(2,random+1)
            if (getCombo(rollResults) == "nessuna combo"){binding.ComboReader.text = getCombo(rollResults)}
            else{binding.ComboReader.text = getCombo(rollResults) .substring(0..(getCombo(rollResults).lastIndex)-1)} //[0..getCombo(rollResults).lastIndex]
            PartialScore=getScore(getCombo(rollResults))
            (activity as MainMenuActivity).editScore(PartialScore)
            binding.Score.text = "${PartialScore} "+ getString(R.string.Points)
            binding.ThirdRoll.isClickable = false
            combo = getCombo(rollResults)
        }
        binding.FourthRoll.setOnClickListener {
            val random = Random().nextInt(6)

            arrayDices[3].setImageResource(diceImages.elementAt(random))
            arrayDices[3].startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.shake_animation))
            rollResults.removeAt(3)
            rollResults.add(3,random+1)
            if (getCombo(rollResults) == "nessuna combo"){binding.ComboReader.text = getCombo(rollResults)}
            else{binding.ComboReader.text = getCombo(rollResults) .substring(0..(getCombo(rollResults).lastIndex)-1)} //[0..getCombo(rollResults).lastIndex]
            PartialScore=getScore(getCombo(rollResults))
            (activity as MainMenuActivity).editScore(PartialScore)
            binding.Score.text = "${PartialScore} "+ getString(R.string.Points)
            binding.FourthRoll.isClickable = false
            combo = getCombo(rollResults)
        }
        binding.FifthRoll.setOnClickListener {
            val random = Random().nextInt(6)

            arrayDices[4].setImageResource(diceImages.elementAt(random))
            arrayDices[4].startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.shake_animation))
            rollResults.removeAt(4)
            rollResults.add(4,random+1)
            if (getCombo(rollResults) == "nessuna combo"){binding.ComboReader.text = getCombo(rollResults)}
            else{binding.ComboReader.text = getCombo(rollResults) .substring(0..(getCombo(rollResults).lastIndex)-1)} //[0..getCombo(rollResults).lastIndex]
            PartialScore=getScore(getCombo(rollResults))
            (activity as MainMenuActivity).editScore(PartialScore)
            binding.Score.text = "${PartialScore} "+ getString(R.string.Points)
            binding.FifthRoll.isClickable = false
            combo = getCombo(rollResults)
        }



        //end roll section

        //return section
        return view
    }



    private fun ScoreUpdate(score: Int, rollResults: MutableList<Int>): Int {
        return score + getScore(getCombo(rollResults))
    }


    //functions
    private fun getRandomValue(dice: ImageView?,list: MutableList<Int>){
        val random = Random().nextInt(6)
        dice?.startAnimation(animation1)
        dice?.setImageResource(diceImages.elementAt(random))
        list.add(random+1)
    } //get and add random value to rollResult. set right diceview
    private fun diceImagesAdder(diceImagesList: MutableList<Int>){
        diceImagesList.add(R.drawable.ic_dice_one)
        diceImagesList.add(R.drawable.ic_dice_two)
        diceImagesList.add(R.drawable.ic_dice_three)
        diceImagesList.add(R.drawable.ic_dice_four)
        diceImagesList.add(R.drawable.ic_dice_five)
        diceImagesList.add(R.drawable.ic_dice_six)
    } //given dicelist, adds to this the dices images of 1 to 6 dices
    private fun getScore(s: String): Int {
        val value = when(s){
            resources.getString(R.string.coppia) -> 5
            resources.getString(R.string.tris)->10
            resources.getString(R.string.quater) -> 20
            resources.getString(R.string.yahtzee) ->50
            resources.getString(R.string.scala_da_4) ->30
            resources.getString(R.string.scala_da_5) -> 40
            resources.getString(R.string.full) ->25
            else -> 0
        }
        return value
    }

    // Combo algorithm fun section
    private fun getCombo(List: MutableList<Int>): String {
        if (FiveASCorder(List)) {
            return resources.getString(R.string.scala_da_5)
        } else {
            if (FourASCorder(List)) {
                return resources.getString(R.string.scala_da_4)
            } else {
                for (i in 1..6) {
                    for (j in 1..6) {
                        if (isYahtzee(List,i)) {
                            return resources.getString(R.string.yahtzee)
                        } else {
                            if (isQuater(List,i)) {
                                return resources.getString(R.string.quater)
                            } else {
                                if (isFull(List)) {
                                    return resources.getString(R.string.full)
                                } else {
                                    if (isTriple(List, i)) {
                                        return resources.getString(R.string.tris)
                                    } else {
                                        if (isDouble(List, i)) {
                                            return resources.getString(R.string.coppia)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return resources.getString(R.string.noCombo)

    }

    private fun isFull(list: MutableList<Int>): Boolean {
        for (i in 1..6){
            if(isTriple(list,i)){
                for(j in 1..6){
                    if((i != j) and isDouble(list,j)){
                        return true
                    }
                }
            }
        }
        return false
    }


    private fun FiveASCorder(list: MutableList<Int>): Boolean {
        val numList = list.sorted()
        var counter = 0
        for(i in 0..3){
            if(numList[i]+1==numList[i+1]){
                counter += 1
            }
            else{
                return false
            }
        }
        if(counter==4){
            return true
        }
        return false
    }

    private fun FourASCorder(list: MutableList<Int>): Boolean {
        val numList = list.sorted()
        for(j in 0..1){
            var counter = 0
            for(i in j..j+2){
                if (numList[i]+1 == numList[i+1]){
                    counter += 1
                }
            }
            if(counter == 3){
                return true
            }
        }
        return false
    }

    private fun isYahtzee(List: MutableList<Int>, number: Int): Boolean {
        var counter = 0
        for (i in 0..4) {
            for (j in i+1..4) {
                if (List[i] == List[j] && List[i] == number) {
                    counter += 1
                }
                if (counter > 6) {
                    return true
                }
            }
        }
        return false
    }

    private fun isQuater(List: MutableList<Int>, number: Int): Boolean {
        var counter = 0
        for (i in 0..4) {
            for (j in i+1..4) {
                if (List[i] == List[j] && List[i] == number) {
                    counter += 1
                }
                if (counter == 6) {
                    return true
                }
            }
        }
        return false
    }

    private fun isTriple(List: MutableList<Int>, number:Int): Boolean {
        var counter = 0
        for (i in 0..4) {
            for (j in i+1..4) {
                if (List[i] == List[j] && List[i] == number) {
                    counter += 1
                }
                if (counter == 3) {
                    return true
                }
            }
        }
        return false
    }

    private fun isDouble(List: MutableList<Int>,number: Int): Boolean {
        var counter = 0
        for (i in 0..4) {
            for (j in i+1..4) {
                if (List[i] == List[j] && List[i] == number) {
                    counter += 1
                }
                if (counter == 1) {
                    return true
                }
            }
        }
        return false
    }

}

